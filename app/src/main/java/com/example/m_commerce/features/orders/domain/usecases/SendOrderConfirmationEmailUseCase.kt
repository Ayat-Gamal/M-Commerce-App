package com.example.m_commerce.features.orders.domain.usecases

import com.example.m_commerce.core.usecase.UseCase
import com.example.m_commerce.features.orders.data.model.MailerEmailRequest
import com.example.m_commerce.features.orders.domain.repository.EmailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendOrderConfirmationEmailUseCase @Inject constructor( private val repo: EmailRepository) : UseCase<MailerEmailRequest, Flow<Result<Unit>>> {

    override fun invoke(request: MailerEmailRequest): Flow<Result<Unit>> {

        return repo.sendEmail(request)
    }
}