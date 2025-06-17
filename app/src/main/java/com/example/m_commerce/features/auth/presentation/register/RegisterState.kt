package com.example.m_commerce.features.auth.presentation.register

import com.google.firebase.auth.FirebaseUser

sealed class RegisterState {
    data object Loading: RegisterState()
    data class Error(val error: Throwable, val message: String): RegisterState()
    data class Success(val user: FirebaseUser?): RegisterState()
    data object Idle : RegisterState()
}