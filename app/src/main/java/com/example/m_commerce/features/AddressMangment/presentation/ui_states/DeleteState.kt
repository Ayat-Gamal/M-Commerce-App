package com.example.m_commerce.features.AddressMangment.presentation.ui_states


sealed class DeleteState {
    object Idle : DeleteState()
    object Loading : DeleteState()
    data class Success(val deletedId: String) : DeleteState()
    data class Error(val message: String) : DeleteState()
}