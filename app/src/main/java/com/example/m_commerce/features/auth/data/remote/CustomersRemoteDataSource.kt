package com.example.m_commerce.features.auth.data.remote

import kotlinx.coroutines.flow.Flow


interface CustomersRemoteDataSource {
    suspend fun createCustomer(email: String, name: String, password: String)
    suspend fun createCustomerToken(email: String, password: String, name: String): Flow<String>
    suspend fun createCustomerCart(token: String): Flow<String>
}