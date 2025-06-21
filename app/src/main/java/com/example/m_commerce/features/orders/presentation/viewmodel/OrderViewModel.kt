package com.example.m_commerce.features.orders.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.BuildConfig
import com.example.m_commerce.features.orders.data.model.EmailRequest
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

        val serviceId = BuildConfig.EMAIL_SERVICE_ID
        val templateId = BuildConfig.EMAIL_TEMPLATE_ID
        val userId = BuildConfig.EMAIL_PUBLIC_KEY
        val templateParams = mapOf(
            "to_name" to "Youssif",
            "from_name" to "M-Commerce App",
            "to_email" to "yuyu.gv45@gmail.com",
//            "order_id" to "#12345",
//            "product_name" to "Wireless Headphones",
//            "order_total" to "EGP 999.00",
//            "tracking_link" to "https://m-commerce.com/track/12345",
            "subject" to "Order Confirmation",
//            "reply_to" to "support@m-commerce.com"
        )

        val request = EmailRequest(
            serviceId = serviceId,
            templateId = templateId,
            userId = userId,
            templateParams = templateParams
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