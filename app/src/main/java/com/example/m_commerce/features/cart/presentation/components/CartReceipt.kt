package com.example.m_commerce.features.cart.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.m_commerce.BuildConfig
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.config.theme.Black
import com.example.m_commerce.config.theme.OfferColor
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.TextBackground
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.features.cart.data.model.ReceiptItem
import com.example.m_commerce.features.cart.presentation.CartUiState
import com.example.m_commerce.features.cart.presentation.viewmodel.CartViewModel
import com.example.m_commerce.features.payment.presentation.screen.createPaymentIntent
import com.example.m_commerce.features.profile.presentation.viewmodel.CurrencyViewModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet


@Composable
fun CartReceipt(
    paddingValues: PaddingValues,
    viewModel: CartViewModel,
    currencyViewModel: CurrencyViewModel,
    paymentSheet: PaymentSheet
) {
    val uiState by viewModel.uiState.collectAsState()
    val cart = (uiState as? CartUiState.Success)?.cart
    var promoCode by remember { mutableStateOf("") }

    val context = LocalContext.current

    var paymentIntentClientSecret by remember { mutableStateOf<String?>(null) }
    val publishableKey = BuildConfig.PAYMENT_PUBLISHABLE_KEY
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
                    viewModel.applyCoupon(promoCode)
                },
                modifier = Modifier.padding(16.dp)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                cart?.let {
                    val exchangeRate by currencyViewModel.exchangeRate.collectAsState()

                    val receiptItems = listOf(
                        ReceiptItem("Subtotal",  currencyViewModel.formatPrice(it.subtotalAmount)),
                        ReceiptItem("Tax", currencyViewModel.formatPrice(it.totalTaxAmount ?: "0.00")),
                        //ReceiptItem("Duties" ,  currencyViewModel.formatPrice(it.totalDutyAmount ?: "0.00" ))
                         ReceiptItem("Discount", currencyViewModel.formatPrice(((it.totalAmount.toFloatOrNull() ?: 0f) - (it.subtotalAmount?.toFloatOrNull() ?: 0f)).toString()), isDiscount = true) )
                    receiptItems.forEach { item ->
                        CartReceiptItem(item)
                    }

                    Divider(Modifier.padding(vertical = 8.dp, horizontal = 8.dp))

                    val convertedTotal = (it.totalAmount.toFloatOrNull() ?: 0f) * exchangeRate

                    CartReceiptItem(
                        ReceiptItem("Total",   currencyViewModel.formatPrice(it.totalAmount ?: "0.00" )))
                }
            }

            var state by remember { mutableStateOf(false) }

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
                onClick = { state = true
                    paymentIntentClientSecret?.let {
                        paymentSheet.presentWithPaymentIntent(
                            it,
                            PaymentSheet.Configuration("My Test Store")
                        )
                    }
                    state = false
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
        Text(text = item.title, fontWeight = FontWeight.Bold , color = if (item.isDiscount)  OfferColor else Teal  )
        Text(text = item.price , color = if (item.isDiscount)  OfferColor else Black
        )
    }
}




