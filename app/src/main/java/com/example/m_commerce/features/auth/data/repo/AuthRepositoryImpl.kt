package com.example.m_commerce.features.auth.data.repo

import com.example.m_commerce.features.auth.data.remote.CustomersRemoteDataSource
import com.example.m_commerce.features.auth.data.remote.UsersRemoteDataSource
import com.example.m_commerce.features.auth.domain.repo.AuthRepository
import com.example.m_commerce.features.auth.presentation.register.AuthState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDataSource: UsersRemoteDataSource,
    private val shopifyDataSource
    : CustomersRemoteDataSource,
) :
    AuthRepository {
    override suspend fun registerUser(email: String, password: String): Flow<AuthState> {
        return userDataSource.registerWithEmail(email, password)
    }

    override suspend fun loginUser(email: String, password: String): Flow<AuthState> {
        return userDataSource.loginUser(email, password)
    }

    override suspend fun sendEmailVerification(user: FirebaseUser): Flow<AuthState> {
        return userDataSource.sendEmailVerification(user)
    }

    override suspend fun createCustomerToken(
        email: String,
        password: String,
        name: String
    ): Flow<String> {
        return shopifyDataSource
            .createCustomerToken(email, password, name)
    }

    override suspend fun createCart(token: String): Flow<String> {
        return shopifyDataSource
            .createCustomerCart(token)
    }

    override suspend fun storeTokenAndCartId(token: String, cartId: String, uid: String) {
        userDataSource.storeTokenAndCartId(token, cartId,uid)
    }


}