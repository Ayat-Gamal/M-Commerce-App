package com.example.m_commerce.features.cart.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.m_commerce.BuildConfig
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.config.theme.Black
import com.example.m_commerce.config.theme.OfferColor
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.TextBackground
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.core.utils.containsPositiveNumber
import com.example.m_commerce.features.cart.data.model.ReceiptItem
import com.example.m_commerce.features.cart.domain.entity.Cart
import com.example.m_commerce.features.cart.presentation.viewmodel.CartViewModel
import com.example.m_commerce.features.orders.data.PaymentMethod
import com.example.m_commerce.features.orders.data.model.variables.LineItem
import com.example.m_commerce.features.orders.presentation.ui_state.OrderUiState
import com.example.m_commerce.features.orders.presentation.viewmodel.OrderViewModel
import com.example.m_commerce.features.payment.presentation.screen.createPaymentIntent
import com.example.m_commerce.features.profile.presentation.viewmodel.CurrencyViewModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import kotlinx.coroutines.launch


@Composable
fun CartReceipt(
    paddingValues: PaddingValues,
    cartViewModel: CartViewModel,
    currencyViewModel: CurrencyViewModel,
    paymentSheet: PaymentSheet,
    orderViewModel: OrderViewModel,
    cart: Cart,
    snackBarHostState: SnackbarHostState
) {
    val uiState by cartViewModel.uiState.collectAsState()
    var promoCode by rememberSaveable { mutableStateOf("") }

    val orderState = orderViewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    var paymentIntentClientSecret by remember { mutableStateOf<String?>(null) }
    val publishableKey = BuildConfig.PAYMENT_PUBLISHABLE_KEY
    val scope = rememberCoroutineScope()
    val showSheet: MutableState<Boolean> = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        PaymentConfiguration.init(context, publishableKey)

        createPaymentIntent { result ->
            result.onSuccess { clientSecret ->
                paymentIntentClientSecret = clientSecret
            }.onFailure { error ->
                error.printStackTrace()
            }
        }
    }

    LaunchedEffect(orderState.value) {
        when (val state = orderState.value) {
            is OrderUiState.Error -> {
                scope.launch {
                    Log.d("Order", "Error: ${state.message}")
                    snackBarHostState.showSnackbar("Error: ${state.message}")
                }
            }

            is OrderUiState.Success -> {
                cartViewModel.clearCart(cart.lines)
            }

            else -> Unit
        }
    }


    Column(modifier = Modifier.background(Background)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp))
                .background(TextBackground)
                .padding(0.dp)
        ) {
            PromoCodeInput(
                promoCode = promoCode,
                onPromoCodeChange = { promoCode = it },
                onApplyClick = {
                    cartViewModel.applyCoupon(promoCode)
                },
                modifier = Modifier.padding(16.dp)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                cart?.let {
                    val exchangeRate by currencyViewModel.exchangeRate.collectAsState()

                    val receiptItems = listOf(
                        ReceiptItem("Subtotal", currencyViewModel.formatPrice(it.subtotalAmount)),
                        ReceiptItem(
                            "Tax",
                            currencyViewModel.formatPrice(it.totalTaxAmount ?: "0.00")
                        ),
                        //ReceiptItem("Duties" ,  currencyViewModel.formatPrice(it.totalDutyAmount ?: "0.00" ))
                        ReceiptItem(
                            "Discount",
                            currencyViewModel.formatPrice(
                                ((it.totalAmount.toFloatOrNull()
                                    ?: 0f) - (it.subtotalAmount?.toFloatOrNull() ?: 0f)).toString()
                            ),
                            isDiscount = true
                        )
                    )
                    receiptItems.forEach { item ->
                        CartReceiptItem(item)
                    }

                    Divider(Modifier.padding(vertical = 8.dp, horizontal = 8.dp))

                    //val convertedTotal = (it.totalAmount.toFloatOrNull() ?: 0f) * exchangeRate

                    CartReceiptItem(
                        ReceiptItem(
                            "Total",
                            currencyViewModel.formatPrice(it.totalAmount ?: "0.00")
                        )
                    )
                }
            }

            var state by remember { mutableStateOf(false) }

            CheckoutBottomSheet(showSheet = showSheet) { paymentMethod ->

                if (!cartViewModel.isConnected()) return@CheckoutBottomSheet

                if (paymentMethod == PaymentMethod.CreditCard) {
                    state = true
                    paymentIntentClientSecret?.let {
                        paymentSheet.presentWithPaymentIntent(
                            it,
                            PaymentSheet.Configuration("My Test Store")
                        )
                    }
                    state = false
                } else {

                    val lineItems = cart.lines.map {
                        LineItem(
                            variantId = it.id,
                            title = it.productTitle,
                            quantity = it.quantity,
                        )

                    }
                    Log.d(
                        "OrderItem",
                        "CartReceipt: ${lineItems.size} ==  ${lineItems[0].variantId} == ${lineItems[0].title} == ${lineItems[0].quantity} == ${lineItems[0].originalUnitPrice} == ${lineItems[0].specs} == ${lineItems[0].image}"
                    )
                    orderViewModel.createOrderAndSendEmail(
                        items = lineItems,
                        paymentMethod = paymentMethod
                    )

//                    if(orderState.value is OrderUiState.Error) {
//                        scope.launch {
//                            snackBarHostState.showSnackbar( "Error: ${(orderState.value as OrderUiState.Error).message}")
//                        }
//                        return@CheckoutBottomSheet
//                    }
//                    cartViewModel.clearCart(cart.lines)
                }
            }

            CustomButton(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding(),
                    top = 16.dp
                ),
                isLoading = state,
                text = "Checkout",
                backgroundColor = Teal,
                textColor = White,
                height = 50,
                cornerRadius = 12,
                onClick = {
                    showSheet.value = true
                }
            )
        }
    }
}


@Composable
fun CartReceiptItem(item: ReceiptItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (containsPositiveNumber(item.price)) {
            Text(
                text = item.title,
                fontWeight = FontWeight.Bold,
                color = if (item.isDiscount) OfferColor else Teal
            )
            Text(
                text = item.price, color = if (item.isDiscount) OfferColor else Black
            )

        }
    }


}




