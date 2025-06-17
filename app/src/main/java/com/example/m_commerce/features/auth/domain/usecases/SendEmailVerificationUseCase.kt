package com.example.m_commerce.features.auth.domain.usecases

import com.example.m_commerce.features.auth.domain.repo.AuthRepository
import com.example.m_commerce.features.auth.presentation.register.RegisterState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class SendEmailVerificationUseCase(private val repo: AuthRepository) {
    suspend fun invoke(user: FirebaseUser): Flow<RegisterState> {
        return repo.sendEmailVerification(user)
    }
}