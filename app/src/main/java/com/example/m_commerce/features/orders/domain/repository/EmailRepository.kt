package com.example.m_commerce.features.orders.domain.repository

import com.example.m_commerce.features.orders.data.model.EmailRequest
import kotlinx.coroutines.flow.Flow

interface EmailRepository {
    suspend fun sendEmail(emailRequest: EmailRequest)
}