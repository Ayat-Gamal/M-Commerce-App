package com.example.m_commerce.features.orders.data.datasources.remote.service

import com.example.m_commerce.features.orders.data.model.MailerEmailRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface MailerService {
    @Headers("Content-Type: application/json")
    @POST("email")
    suspend fun sendEmail(
        @Body emailRequest: MailerEmailRequest,
        @Header("Authorization") apiKey: String
    ): Response<Void>
}
