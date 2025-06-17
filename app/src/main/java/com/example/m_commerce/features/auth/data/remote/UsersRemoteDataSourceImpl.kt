package com.example.m_commerce.features.auth.data.remote

import com.example.m_commerce.features.auth.presentation.register.RegisterState
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersRemoteDataSourceImpl @Inject constructor(private val auth: FirebaseAuth) :
    UsersRemoteDataSource {
    override suspend fun registerWithEmail(email: String, password: String): Flow<RegisterState> =
        flow {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                emit(RegisterState.Success(result.user))
            } catch (e: Exception) {
                val message = when (e) {
                    is FirebaseAuthUserCollisionException -> "This email is already registered."
                    is FirebaseNetworkException -> "No internet connection. Please try again."
                    else -> "Registration failed: ${e.localizedMessage ?: "Unknown error"}"
                }
                emit(RegisterState.Error(e, message))
            }
        }


    override suspend fun sendEmailVerification(user: FirebaseUser): Flow<RegisterState> =
        flow {
            try {
                user.sendEmailVerification().await()
                emit(RegisterState.Success(user))
            } catch (e: Exception) {
                emit(RegisterState.Error(e, "Failed to send verification email"))
            }
        }
}