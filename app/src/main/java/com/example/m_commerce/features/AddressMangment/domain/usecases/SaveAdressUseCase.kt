package com.example.m_commerce.features.AddressMangment.domain.usecases

import com.example.m_commerce.features.AddressMangment.domain.entity.Address
import com.example.m_commerce.features.AddressMangment.domain.entity.Response
import com.example.m_commerce.features.AddressMangment.domain.repository.AddressRepository
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SaveAddressUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(address: Address): Flow<Response<Unit>> {
        return repository.saveAddress(address)
    }
}