package com.example.m_commerce.features.auth.data.repo

import com.example.m_commerce.features.auth.data.remote.UsersRemoteDataSource
import com.example.m_commerce.features.auth.domain.repo.AuthRepository
import com.example.m_commerce.features.auth.presentation.register.RegisterState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val remote: UsersRemoteDataSource) :
    AuthRepository {
    override suspend fun registerUser(email: String, password: String): Flow<RegisterState> {
        return remote.registerWithEmail(email, password)
    }

    override suspend fun sendEmailVerification(user: FirebaseUser): Flow<RegisterState> {
        return remote.sendEmailVerification(user)
    }

}