package com.example.m_commerce.features.auth.presentation.register

import com.google.firebase.auth.FirebaseUser

sealed class AuthState {
    data object Loading: AuthState()
    data class Error(val error: Throwable, val message: String): AuthState()
    data class Success(val user: FirebaseUser?): AuthState()
    data object Idle : AuthState()
}