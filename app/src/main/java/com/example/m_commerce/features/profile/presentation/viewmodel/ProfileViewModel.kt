package com.example.m_commerce.features.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.core.utils.NetworkManager
import com.example.m_commerce.features.AddressMangment.domain.usecases.GetCustomerNameUseCase
import com.example.m_commerce.features.product.presentation.SnackBarMessage
import com.example.m_commerce.features.profile.presentation.state.ProfileUiState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCustomerName: GetCustomerNameUseCase,
    private val networkManager: NetworkManager,
    ) : ViewModel() {
    private val _userNameState = MutableStateFlow("")
    val userNameState = _userNameState.asStateFlow()


     fun loadName() = viewModelScope.launch {
        val name  = getCustomerName().firstOrNull()
        _userNameState.emit(name ?: "Guest")
    }

    fun isConnected(): Boolean {
        return networkManager.isNetworkAvailable()
    }
}
