package com.example.m_commerce.features.auth.domain.usecases

import com.example.m_commerce.features.auth.domain.repo.AuthRepository
import kotlinx.coroutines.flow.Flow

class CreateCustomerTokenUseCase(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, name: String): Flow<String> {
        return repo.createCustomerToken(email, password, name)
    }
}