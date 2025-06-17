package com.example.m_commerce.features.auth.domain.repo

import com.example.m_commerce.features.auth.presentation.register.RegisterState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun registerUser(email: String, password: String): Flow<RegisterState>
    suspend fun sendEmailVerification(user: FirebaseUser) : Flow<RegisterState>
}