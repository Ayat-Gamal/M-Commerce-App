package com.example.m_commerce.features.orders.domain.usecases

import com.example.m_commerce.core.usecase.UseCase
import com.example.m_commerce.features.orders.data.model.EmailRequest
import com.example.m_commerce.features.orders.domain.repository.EmailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendOrderConfirmationEmailUseCase @Inject constructor( private val repo: EmailRepository) {

    suspend operator fun invoke(request: EmailRequest) {

        return repo.sendEmail(request)
    }
}