package com.example.m_commerce.features.orders.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.BuildConfig
import com.example.m_commerce.features.orders.data.model.EmailPersonalization
import com.example.m_commerce.features.orders.data.model.MailerEmailRequest
import com.example.m_commerce.features.orders.domain.usecases.SendOrderConfirmationEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val sendEmailUseCase: SendOrderConfirmationEmailUseCase) : ViewModel() {


    init {
        sendEmail()
    }

    fun sendEmail() {

        val request = MailerEmailRequest(
            from = EmailPersonalization("example.mailersend.net", "M-Commerce"),
            to = listOf(EmailPersonalization("youssifn.mostafa@gmail.com", "Youssif")),
            subject = "Order Confirmation",
            html = """
                <h2>Hello Youssif 👋</h2>
                <p>Thank you for your order!</p>
                <p><strong>Order ID:</strong> #12345<br>
                <strong>Total:</strong> EGP 999.00<br>
                <a href="https://m-commerce.com/track/12345">Track your order</a></p>
            """.trimIndent()
        )

        viewModelScope.launch {
            sendEmailUseCase(request).collect { result ->
                result.onSuccess {

                    Log.d("EmailJS", "Email sent successfully ${result.isSuccess}")
                }.onFailure {
                    Log.e("EmailJS", "Send failed: ${it.message}")
                    Log.d("EmailJS_REQUEST", request.toString())

                    Log.e("EmailJS", "Error: ${it.cause?.message} - ${result.isFailure}")


                }
            }
        }

    }
}