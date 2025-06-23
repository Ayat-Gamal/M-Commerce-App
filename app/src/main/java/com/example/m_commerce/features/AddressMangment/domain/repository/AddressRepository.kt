package com.example.m_commerce.features.AddressMangment.domain.repository

import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID

interface AddressRepository {
    suspend fun saveAddress(address: Storefront.MailingAddressInput)
     suspend fun getCustomerAddresses(
         onResult: (List<Storefront.MailingAddress>) -> Unit
     )
    suspend fun setDefaultAddress( id: ID)
    suspend fun getDefaultAddress(
        onResult: (Storefront.MailingAddress) -> Unit
    )
}
