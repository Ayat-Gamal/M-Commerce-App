package com.example.m_commerce.features.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.m_commerce.features.profile.presentation.state.ProfileUiState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    private val _profileState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val profileState: StateFlow<ProfileUiState> = _profileState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        _profileState.value = when {
            currentUser == null -> ProfileUiState.Guest
            else -> ProfileUiState.Success(
                profileName = currentUser.displayName ?: "",
                profileImageUrl = currentUser.photoUrl?.toString()
                    ?: "https://i.pinimg.com/736x/17/c0/d1/17c0d1bfcef18ad4a83d5b5b95f328df.jpg"
            )
        }
    }
}
