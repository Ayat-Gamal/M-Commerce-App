package com.example.m_commerce.features.AddressMangment.domain.entity

import com.shopify.graphql.support.ID

data class Address(
    val id: String = "",
    val firstName: String,
    val lastName: String,
    val address1: String,
    val address2: String = "",
    val city: String,
    val zip: String,
    val country: String,
    val phone: String? = null
)

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val message: String) : Response<Nothing>()
    object Loading : Response<Nothing>()
}