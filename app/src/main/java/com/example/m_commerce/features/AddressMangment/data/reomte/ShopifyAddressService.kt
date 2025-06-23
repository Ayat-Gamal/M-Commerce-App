package com.example.m_commerce.features.AddressMangment.data.reomte

import com.shopify.buy3.Storefront

interface ShopifyAddressService {
    suspend fun saveAddress(address: Storefront.MailingAddressInput)

}