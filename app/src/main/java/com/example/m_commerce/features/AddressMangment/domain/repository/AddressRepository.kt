package com.example.m_commerce.features.AddressMangment.domain.repository

import com.example.m_commerce.features.AddressMangment.domain.entity.Address
import com.example.m_commerce.features.AddressMangment.domain.entity.DeleteResponse
import com.example.m_commerce.features.AddressMangment.domain.entity.Response
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    suspend fun saveAddress(address: Address): Flow<Response<Unit>>
    suspend fun getAddresses(): Flow<Response<List<Address>>>
    suspend fun setDefaultAddress(addressId: String): Flow<Response<Unit>>
    suspend fun getDefaultAddress(): Flow<Response<Address>>
    suspend fun deleteAddress(addressId: String): Flow<Response<DeleteResponse>>
}
