package com.example.m_commerce.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.auth.domain.usecases.LoginUserUseCase
import com.example.m_commerce.features.auth.domain.validation.ValidateEmail
import com.example.m_commerce.features.auth.domain.validation.ValidatePassword
import com.example.m_commerce.features.auth.domain.validation.ValidationResult
import com.example.m_commerce.features.auth.presentation.register.AuthState
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val loginUser: LoginUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthState>(AuthState.Idle)
    val uiState = _uiState.asStateFlow()

    private var _messageState = MutableSharedFlow<String>()
    val messageState = _messageState.asSharedFlow()

    fun login(email: String, password: String) {

        val result = validate(email, password)

        viewModelScope.launch {
            if (result.successful) {
                _uiState.value = AuthState.Loading
                loginUser(email, password)
                    .catch { e ->
                        when (e) {
                            is FirebaseAuthInvalidUserException -> emit(
                                AuthState.Error(
                                    e,
                                    "User not found"
                                )
                            )

                            is FirebaseAuthInvalidCredentialsException -> emit(
                                AuthState.Error(
                                    e,
                                    "Invalid email format or incorrect password"
                                )
                            )

                            is FirebaseNetworkException -> emit(
                                AuthState.Error(
                                    e,
                                    "No internet or unstable connection"
                                )
                            )

                            is FirebaseTooManyRequestsException -> emit(
                                AuthState.Error(
                                    e,
                                    "Too many login attempts in a short time, try again later"
                                )
                            )

                            else -> emit(AuthState.Error(e, "Login failed: ${e.localizedMessage}"))
                        }
                    }
                    .collect {
                        _uiState.emit(it)
                    }
            } else {
                _messageState.emit(result.errorMessage!!)
            }
        }
    }

    private fun validate(email: String, password: String): ValidationResult {
        val emailResult = validateEmail(email)
        if (!emailResult.successful) return emailResult

        val passwordResult = validatePassword(password)
        if (!passwordResult.successful) return passwordResult

        return ValidationResult(true)
    }
}