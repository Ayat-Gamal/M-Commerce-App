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
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


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



fun createPaymentIntent(
    amount: Int = 10000,
    currency: String = "usd",
    secretKey: String = BuildConfig.PAYMENT_SECRET_KEY,
    callback: (Result<String>) -> Unit
) {
    val client = OkHttpClient()

    val formBody = FormBody.Builder()
        .add("amount", amount.toString())
        .add("currency", currency)
        .build()

    val request = Request.Builder()
        .url("https://api.stripe.com/v1/payment_intents")
        .addHeader("Authorization", Credentials.basic(secretKey, ""))
        .post(formBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            callback(Result.failure(e))
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    callback(Result.failure(IOException("Unexpected code $response")))
                    return
                }

                val bodyString = response.body?.string()
                val clientSecret = Regex("\"client_secret\"\\s*:\\s*\"(pi_[^\"\\\\]+)\"")
                    .find(bodyString ?: "")
                    ?.groups?.get(1)?.value

                if (clientSecret != null) {
                    callback(Result.success(clientSecret))
                } else {
                    callback(Result.failure(Exception("client_secret not found in response")))
                }
            }
        }
    })
}

