package com.example.m_commerce.features.AddressMangment.domain.usecases

import com.example.m_commerce.features.AddressMangment.domain.repository.AddressRepository
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import javax.inject.Inject


// flag
class SaveAddressUseCase @Inject constructor(private val repository: AddressRepository) {
    suspend operator fun invoke(address: Storefront.MailingAddressInput) = repository.saveAddress( address)
}
class GetCustomerAddressesUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(
        onResult: (List<Storefront.MailingAddress>) -> Unit
    ) {
        repository.getCustomerAddresses( onResult)
    }
}

class SetDefaultAddressUseCase @Inject constructor(private val repository: AddressRepository) {
    suspend operator fun invoke(id: ID) = repository.setDefaultAddress(id)
}

class GetDefaultAddressUseCase @Inject constructor(private val repository: AddressRepository) {
    suspend operator fun invoke(onResult: (Storefront.MailingAddress) -> Unit) {
        repository.getDefaultAddress(onResult)
    }
}