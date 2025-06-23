package com.example.m_commerce.features.AddressMangment.data.model

import com.shopify.graphql.support.ID

data class AddressEntity(
    val id: ID? = null,
    val address1: String,
    val city: String,
    val country: String,
    val firstName: String,
    val lastName: String,
    val zip: String,
    val phone: String? = null
)