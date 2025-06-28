package com.example.m_commerce.features.payment.presentation.screen


import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.m_commerce.BuildConfig
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet


@Composable
fun PaymentScreenUI(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    paymentSheet: PaymentSheet
) {
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

    Button(onClick = {
        paymentIntentClientSecret?.let {
            paymentSheet.presentWithPaymentIntent(
                it,
                PaymentSheet.Configuration("My Test Store")
            )
        }
    }) {
        Text("Checkout")
    }
}




