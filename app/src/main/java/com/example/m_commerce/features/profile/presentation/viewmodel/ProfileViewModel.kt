package com.example.m_commerce.features.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.m_commerce.features.profile.domain.entity.ProfileOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {
    private val _profileState = MutableStateFlow(ProfileUiState())
    val profileState: StateFlow<ProfileUiState> = _profileState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        _profileState.value = ProfileUiState(
            profileName = "UserName",
        )
    }
}

data class ProfileUiState(
    val profileName: String = "",
    val profileOptions: List<ProfileOption> = emptyList()
)