package com.example.m_commerce.features.AddressMangment.data.repositoryImpl

import com.example.m_commerce.features.AddressMangment.data.reomte.ShopifyAddressServiceImpl
import com.example.m_commerce.features.AddressMangment.domain.repository.AddressRepository
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val service: ShopifyAddressServiceImpl
) : AddressRepository {

    override suspend fun saveAddress(address: Storefront.MailingAddressInput) = service.saveAddress( address)

    override suspend fun getCustomerAddresses(
        onResult: (List<Storefront.MailingAddress>) -> Unit
    ) {
        service.getCustomerAddresses(onResult)
    }
    override suspend fun setDefaultAddress(id: ID) = service.setDefaultAddress( id)
    override suspend fun getDefaultAddress(onResult: (Storefront.MailingAddress) -> Unit) = service.getDefaultAddress( onResult)

}