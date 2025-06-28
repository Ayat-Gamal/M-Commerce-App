package com.example.m_commerce.features.AddressMangment.domain.usecases

import com.example.m_commerce.features.AddressMangment.domain.entity.DeleteResponse
import com.example.m_commerce.features.AddressMangment.domain.entity.Response
import com.example.m_commerce.features.AddressMangment.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class DeleteAddressUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(addressId: String): Flow<Response<DeleteResponse>> {
        return repository.deleteAddress(addressId)
            .catch { e ->
                emit(Response.Error(e.message ?: "UseCase deletion error"))
            }
    }
}