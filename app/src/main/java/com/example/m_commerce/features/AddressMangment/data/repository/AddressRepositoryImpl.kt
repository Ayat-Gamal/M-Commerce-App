package com.example.m_commerce.features.AddressMangment.data.repository

import com.example.m_commerce.features.AddressMangment.data.remote.AddressDataSource
import com.example.m_commerce.features.AddressMangment.domain.entity.Address
import com.example.m_commerce.features.AddressMangment.domain.entity.DeleteResponse
import com.example.m_commerce.features.AddressMangment.domain.entity.Response
import com.example.m_commerce.features.AddressMangment.domain.repository.AddressRepository
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val remote: AddressDataSource
) : AddressRepository {

    override suspend fun saveAddress(address: Address): Flow<Response<Unit>> {
        return remote.saveAddress(address)
    }

    override suspend fun getAddresses(): Flow<Response<List<Address>>> {
        return remote.getAddresses()
    }
    override suspend fun setDefaultAddress(addressId: String): Flow<Response<Unit>> {
        return remote.setDefaultAddress(addressId)
    }

    override suspend fun getDefaultAddress(): Flow<Response<Address>> {
        return remote.getDefaultAddress()
    }
    override suspend fun deleteAddress(addressId: String): Flow<Response<DeleteResponse>> {
        return remote.deleteAddress(addressId)
            .catch { e ->
                emit(Response.Error(e.message ?: "Repository deletion error"))
            }
    }
}