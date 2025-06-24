package com.example.m_commerce.features.AddressMangment.domain.usecases

import com.example.m_commerce.features.AddressMangment.domain.entity.Response
import com.example.m_commerce.features.AddressMangment.domain.repository.AddressRepository
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject



class SetDefaultAddressUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    operator suspend fun invoke(addressId: String): Flow<Response<Unit>> {
        return repository.setDefaultAddress(addressId)
    }
}