package com.example.m_commerce.features.AddressMangment.di

import com.example.m_commerce.features.AddressMangment.data.reomte.ShopifyAddressService
import com.example.m_commerce.features.AddressMangment.data.reomte.ShopifyAddressServiceImpl
import com.example.m_commerce.features.AddressMangment.data.repositoryImpl.AddressRepositoryImpl
import com.example.m_commerce.features.AddressMangment.domain.repository.AddressRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindShopifyAddressService(repo: ShopifyAddressServiceImpl): ShopifyAddressService

    @Binds
    abstract fun bindAddressRepository(repo: AddressRepositoryImpl): AddressRepository



}