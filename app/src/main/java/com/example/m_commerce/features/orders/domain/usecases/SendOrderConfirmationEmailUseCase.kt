package com.example.m_commerce.features.orders.domain.usecases

import com.example.m_commerce.core.usecase.UseCase
import com.example.m_commerce.features.orders.data.model.EmailRequest
import com.example.m_commerce.features.orders.domain.repository.EmailJsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendOrderConfirmationEmailUseCase @Inject constructor( private val repo: EmailJsRepository) : UseCase<EmailRequest, Flow<Result<Unit>>> {

    override fun invoke(request: EmailRequest): Flow<Result<Unit>> {

        return repo.sendEmail(request)
    }
}