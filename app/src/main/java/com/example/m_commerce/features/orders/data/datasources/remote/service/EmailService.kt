package com.example.m_commerce.features.orders.data.datasources.remote.service

import com.example.m_commerce.features.orders.data.model.EmailRequest
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

class EmailDao {
}
interface EmailJsService{
    @POST("api/v1.0/email/send")
    suspend fun sendEmail(@Body body: EmailRequest): Response<Void>
}

