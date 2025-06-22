package com.example.m_commerce.features.orders.data.repository

import android.util.Log
import com.example.m_commerce.features.orders.data.datasources.remote.service.MailerService
import com.example.m_commerce.features.orders.data.model.MailerEmailRequest
import com.example.m_commerce.features.orders.domain.repository.EmailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EmailRepositoryImpl @Inject constructor(
    private val service: MailerService
) : EmailRepository {
    override fun sendEmail(emailRequest: MailerEmailRequest): Flow<Result<Unit>> = flow {

        val response = service.sendEmail(emailRequest, "Bearer mlsn.5bd0dfe9d191db3accb8fec9596776207c3a533a1784ff56805602578ab24a82")
        if (response.isSuccessful) {
            emit(Result.success(Unit))
        } else {
            emit(Result.failure(Exception("Error ${response.code()}-- ${response.errorBody()?.string()}")))
            Log.e("ErrorJS","${response.code()} -- ${response.message()}  -- ${response.errorBody()?.string()}")
        }
    }.flowOn(Dispatchers.IO)
}
