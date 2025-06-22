package com.example.m_commerce.features.orders.data.datasources.remote

import com.example.m_commerce.features.orders.data.model.EmailRequest

interface OrderEmailService {
    suspend fun sendEmail(emailRequest: EmailRequest)
}