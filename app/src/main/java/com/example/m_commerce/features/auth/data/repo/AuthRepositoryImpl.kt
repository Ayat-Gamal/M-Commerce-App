package com.example.m_commerce.features.auth.data.repo

import com.example.m_commerce.features.auth.data.remote.CustomersRemoteDataSource
import com.example.m_commerce.features.auth.data.remote.UsersRemoteDataSource
import com.example.m_commerce.features.auth.domain.repo.AuthRepository
import com.example.m_commerce.features.auth.presentation.register.AuthState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remote: UsersRemoteDataSource,
    private val shopify: CustomersRemoteDataSource,
) :
    AuthRepository {
    override suspend fun registerUser(email: String, password: String): Flow<AuthState> {
        return remote.registerWithEmail(email, password)
    }

    override suspend fun loginUser(email: String, password: String): Flow<AuthState> {
        return remote.loginUser(email, password)
    }

    override suspend fun sendEmailVerification(user: FirebaseUser): Flow<AuthState> {
        return remote.sendEmailVerification(user)
    }

    override suspend fun createCustomerToken(
        email: String,
        password: String,
        name: String
    ): Flow<String> {
        return shopify.createCustomerToken(email, password, name)
    }


}