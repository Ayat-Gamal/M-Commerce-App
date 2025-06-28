package com.example.m_commerce.features.profile.presentation.state

sealed class ProfileUiState {
    data object Loading : ProfileUiState()
    data object Guest : ProfileUiState()
    data object NoNetwork : ProfileUiState()
    data object Empty : ProfileUiState()
    data class Error(val error: String) : ProfileUiState()
    data class Success(
        val profileName: String,
        val profileImageUrl: String // Add this
    ) : ProfileUiState()
}
