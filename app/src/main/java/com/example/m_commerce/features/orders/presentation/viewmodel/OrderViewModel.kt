package com.example.m_commerce.features.orders.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.orders.data.model.EmailContent
import com.example.m_commerce.features.orders.data.model.EmailRequest
import com.example.m_commerce.features.orders.domain.usecases.SendOrderConfirmationEmailUseCase
import com.google.type.DateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class OrderViewModel @Inject constructor(private val sendEmailUseCase: SendOrderConfirmationEmailUseCase) : ViewModel() {


    init {
        sendEmail()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendEmail() = viewModelScope.launch {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm")
        val currentTime = "LocalTime.now().format(formatter)"
        val content = EmailContent(
            toName = "current user name",
            fromName = "shop name",
            orderId = "oId",
            orderDate = currentTime,
            productName = "",
            total = ""
        )
        val request = EmailRequest(to = listOf("yuyu.gv45@gmail.com"), subject = "", content = content, html = "")

        sendEmailUseCase(request)

    }
}