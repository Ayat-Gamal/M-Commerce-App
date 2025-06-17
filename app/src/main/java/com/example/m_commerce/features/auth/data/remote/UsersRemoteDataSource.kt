package com.example.m_commerce.features.auth.data.remote

import com.example.m_commerce.features.auth.presentation.register.RegisterState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UsersRemoteDataSource {
    suspend fun registerWithEmail(email: String, password: String): Flow<RegisterState>
    suspend fun sendEmailVerification(user: FirebaseUser): Flow<RegisterState>
}