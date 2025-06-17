package com.example.m_commerce.features.auth.domain.usecases

import com.example.m_commerce.features.auth.domain.repo.AuthRepository
import com.example.m_commerce.features.auth.presentation.register.RegisterState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repo: AuthRepository) {
    suspend fun invoke(email: String, password: String): Flow<RegisterState> {
        return repo.registerUser(email, password)
    }
}