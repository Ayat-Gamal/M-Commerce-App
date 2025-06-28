package com.example.m_commerce.features.auth.domain.usecases

import com.example.m_commerce.features.auth.domain.repo.AuthRepository
import com.example.m_commerce.features.auth.presentation.register.AuthState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Flow<AuthState> {
        return repo.loginUser(email, password)
    }
}