package com.example.m_commerce.features.orders.data.repository

import android.util.Log
import com.example.m_commerce.features.orders.data.datasources.remote.service.EmailJsService
import com.example.m_commerce.features.orders.data.model.EmailRequest
import com.example.m_commerce.features.orders.domain.repository.EmailJsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EmailJsRepositoryImpl @Inject constructor(
    private val service: EmailJsService
) : EmailJsRepository {
    override fun sendEmail(emailRequest: EmailRequest): Flow<Result<Unit>> = flow {

        val response = service.sendEmail(emailRequest)
        if (response.isSuccessful) {
            emit(Result.success(Unit))
        } else {
            emit(Result.failure(Exception("Error ${response.code()}-- ${response.errorBody()?.string()}")))
            Log.e("ErrorJS","${response.code()} -- ${response.message()}  -- ${response.errorBody()?.string()}")
        }
    }.flowOn(Dispatchers.IO)
}
