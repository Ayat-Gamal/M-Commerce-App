package com.example.m_commerce.features.AddressMangment.data.model

import com.shopify.graphql.support.ID

data class AddressEntity(
    val id: ID? = null,
    val firstName: String,
    val lastName: String,
    val address1: String,
    val city: String,
    val zip: String,
    val country: String?,
    val phone: String? = null
)