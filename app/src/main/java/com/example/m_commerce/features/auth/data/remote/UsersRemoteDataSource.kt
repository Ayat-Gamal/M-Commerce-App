package com.example.m_commerce.features.auth.data.remote

import com.example.m_commerce.features.auth.presentation.register.AuthState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UsersRemoteDataSource {
    suspend fun registerWithEmail(email: String, password: String): Flow<AuthState>
    suspend fun sendEmailVerification(user: FirebaseUser): Flow<AuthState>
    suspend fun loginUser(email: String, password: String): Flow<AuthState>
    suspend fun storeTokenAndCartId(token: String, cartId: String, uid: String)
}