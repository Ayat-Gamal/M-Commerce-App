package com.example.m_commerce.features.orders.data.repository

import com.example.m_commerce.features.orders.data.datasources.remote.OrderEmailService
import com.example.m_commerce.features.orders.data.model.EmailRequest
import com.example.m_commerce.features.orders.domain.repository.EmailRepository
import javax.inject.Inject

class EmailRepositoryImpl @Inject constructor(
    private val service: OrderEmailService
) : EmailRepository {
    override suspend fun sendEmail(emailRequest: EmailRequest) {
        service.sendEmail(emailRequest)
    }
}
