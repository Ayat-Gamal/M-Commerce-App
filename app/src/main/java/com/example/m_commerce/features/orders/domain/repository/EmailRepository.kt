package com.example.m_commerce.features.orders.domain.repository

import com.example.m_commerce.features.orders.data.model.MailerEmailRequest
import kotlinx.coroutines.flow.Flow

interface EmailRepository {
    fun sendEmail(emailRequest: MailerEmailRequest) : Flow<Result<Unit>>
}