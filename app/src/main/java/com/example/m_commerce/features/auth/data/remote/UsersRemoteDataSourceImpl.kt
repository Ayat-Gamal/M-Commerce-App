package com.example.m_commerce.features.auth.data.remote

import com.example.m_commerce.features.auth.presentation.register.AuthState
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersRemoteDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) :
    UsersRemoteDataSource {
    override suspend fun registerWithEmail(email: String, password: String): Flow<AuthState> =
        flow {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                emit(AuthState.Success(result.user))
            } catch (e: Exception) {
                val message = when (e) {
                    is FirebaseAuthUserCollisionException -> "This email is already registered."
                    is FirebaseNetworkException -> "No internet connection. Please try again."
                    else -> "Registration failed: ${e.localizedMessage ?: "Unknown error"}"
                }
                emit(AuthState.Error(e, message))
            }
        }

    override suspend fun sendEmailVerification(user: FirebaseUser): Flow<AuthState> = flow {
        val result = try {
            user.sendEmailVerification().await()
            AuthState.Success(user)
        } catch (e: Exception) {
            AuthState.Error(e, "Failed to send verification email")
        }

        emit(result)
    }

    override suspend fun loginUser(email: String, password: String): Flow<AuthState> = flow {
        val result = try {
            auth.signInWithEmailAndPassword(email, password).await()

        } catch (e: Exception) {
            when (e) {
                is FirebaseAuthInvalidUserException -> {
                    emit(AuthState.Error(e, "User not found")); return@flow
                }

                is FirebaseAuthInvalidCredentialsException -> {
                    emit(
                        AuthState.Error(
                            e,
                            "Invalid email format or incorrect password"
                        )
                    ); return@flow
                }

                is FirebaseNetworkException -> {
                    emit(AuthState.Error(e, "No internet or unstable connection")); return@flow
                }

                is FirebaseTooManyRequestsException -> {
                    emit(
                        AuthState.Error(
                            e,
                            "Too many login attempts in a short time, try again later"
                        )
                    ); return@flow
                }

                else -> {
                    emit(AuthState.Error(e, "Login failed: ${e.localizedMessage}")); return@flow
                }
            }
        }

        if (result.user?.isEmailVerified == true) {
            emit(AuthState.Success(result.user))
        } else {
            auth.signOut()
            emit(AuthState.Error(Exception(), "Please activate your email"))
        }
    }

    override suspend fun storeTokenAndCartId(token: String, cartId: String, uid: String) {
        val data = hashMapOf(
            "shopifyToken" to token,
            "cartId" to cartId,
        )
        db.collection("users")
            .document(uid)
            .set(data)
            .await()
    }
}