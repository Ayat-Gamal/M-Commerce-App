package com.example.m_commerce.features.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.auth.domain.usecases.RegisterUserUseCase
import com.example.m_commerce.features.auth.domain.usecases.SendEmailVerificationUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUser: RegisterUserUseCase,
    private val sendEmailVerification: SendEmailVerificationUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState = _registerState.asStateFlow()

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registerState.emit(RegisterState.Loading)
            registerUser.invoke(email, password).catch { e ->
                _registerState.emit(
                    RegisterState.Error(
                        e, "Registration failed: ${e.localizedMessage} ?: Unknown error"
                    )
                )
            }.collect { result ->
                if (result is RegisterState.Success) {
                    sendEmailVerification(result.user)
                } else if (result is RegisterState.Error) {
                    _registerState.emit(RegisterState.Error(result.error, result.message))
                }
            }
        }
    }

    private suspend fun sendEmailVerification(user: FirebaseUser?) {
        user?.let {
            sendEmailVerification.invoke(it).collect { result ->
                if (result is RegisterState.Success) _registerState.emit(
                    RegisterState.Success(
                        result.user
                    )
                )
                else if (result is RegisterState.Error) _registerState.emit(
                    RegisterState.Error(
                        result.error, result.message
                    )
                )
            }
        }
    }
}