package com.example.m_commerce.features.auth.presentation.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.auth.domain.usecases.CreateCartUseCase
import com.example.m_commerce.features.auth.domain.usecases.CreateCustomerTokenUseCase
import com.example.m_commerce.features.auth.domain.usecases.RegisterUserUseCase
import com.example.m_commerce.features.auth.domain.usecases.SendEmailVerificationUseCase
import com.example.m_commerce.features.auth.domain.usecases.StoreTokenAndCartIdUseCase
import com.example.m_commerce.features.auth.domain.validation.ValidateConfirmPassword
import com.example.m_commerce.features.auth.domain.validation.ValidateEmail
import com.example.m_commerce.features.auth.domain.validation.ValidateName
import com.example.m_commerce.features.auth.domain.validation.ValidatePassword
import com.example.m_commerce.features.auth.domain.validation.ValidationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUser: RegisterUserUseCase,
    private val sendEmailVerification: SendEmailVerificationUseCase,
    private val validateName: ValidateName,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateConfirmPassword: ValidateConfirmPassword,
    private val createCustomerToken: CreateCustomerTokenUseCase,
    private val createCart: CreateCartUseCase,
    private val storeTokenAndCartId: StoreTokenAndCartIdUseCase,
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val registerState = _authState.asStateFlow()

    private val _messageState = MutableSharedFlow<String>()
    val messageState = _messageState.asSharedFlow()

    fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val result = validate(name, email, password, confirmPassword)
        viewModelScope.launch {
            if (result.successful) {
                registerUserInFirebase(email, password, name)
            } else _messageState.emit(result.errorMessage!!)
        }
    }

    private suspend fun registerUserInFirebase(email: String, password: String, name: String) {
        _authState.emit(AuthState.Loading)
        registerUser.invoke(email, password)
            .catch { e ->
                _authState.emit(
                    AuthState.Error(
                        e, "Registration failed: ${e.localizedMessage} ?: Unknown error"
                    )
                )
            }
            .collect { result ->
                if (result is AuthState.Success) {
                    val user = result.user
                    user?.let { setupUser(email, password, name, it) }
                } else if (result is AuthState.Error) {
                    _authState.emit(AuthState.Error(result.error, result.message))
                }
            }
    }

    private fun setupUser(email: String, password: String, name: String, user: FirebaseUser) =
        viewModelScope.launch {
            Log.i("TAG", "setupUser: called")
            sendEmailVerification(user)
            Log.i("TAG", "setupUser: sendEmailVerification done")
            createCustomerToken(email, password, name).collect { token ->
                Log.i("TAG", "token: $token")
                createCart(token).collect { cartId ->
                    Log.i("TAG", "cartId: $cartId")
                    storeTokenAndCartId(token, cartId, user.uid)
                    Log.i("TAG", "token and cart it stored")
                    Log.i("TAG", "setupUser: token: $token, cart id: $cartId, email: ${user.email}")
                }
                FirebaseAuth.getInstance().signOut()
            }
        }


    private suspend fun sendEmailVerification(user: FirebaseUser?) {
        user?.let {
            sendEmailVerification.invoke(it).collect { result ->
                if (result is AuthState.Success) {
                    _authState.emit(AuthState.Success(result.user))
                    FirebaseAuth.getInstance().signOut()
                } else if (result is AuthState.Error) _authState.emit(
                    AuthState.Error(
                        result.error, result.message
                    )
                )
            }
        }
    }

    private fun validate(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): ValidationResult {
        val nameResult = validateName(name)
        if (!nameResult.successful) return nameResult

        val emailResult = validateEmail(email)
        if (!emailResult.successful) return emailResult

        val passwordResult = validatePassword(password)
        if (!passwordResult.successful) return passwordResult

        val confirmPasswordResult = validateConfirmPassword(password, confirmPassword)
        if (!confirmPasswordResult.successful) return confirmPasswordResult

        return ValidationResult(true)
    }
}