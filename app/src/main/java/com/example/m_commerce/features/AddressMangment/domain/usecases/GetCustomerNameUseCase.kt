package com.example.m_commerce.features.AddressMangment.domain.usecases

import com.example.m_commerce.features.AddressMangment.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCustomerNameUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    operator fun invoke(): Flow<String> {
        return repository.getCustomerName()
    }
}