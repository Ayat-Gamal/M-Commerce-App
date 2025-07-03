package com.example.m_commerce.features.AddressMangment.domain.usecases

import com.example.m_commerce.features.AddressMangment.domain.entity.Address
import com.example.m_commerce.features.AddressMangment.domain.entity.Response
import com.example.m_commerce.features.AddressMangment.domain.repository.AddressRepository
import com.shopify.buy3.Storefront
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAddressesUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    operator suspend fun invoke(): Flow<Response<List<Address>>> {
        return repository.getAddresses()
    }
}