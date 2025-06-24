package com.example.m_commerce.features.AddressMangment.di

import com.example.m_commerce.features.AddressMangment.data.remote.AddressDataSource
import com.example.m_commerce.features.AddressMangment.data.remote.AddressDataSourceImpl
import com.example.m_commerce.features.AddressMangment.data.repository.AddressRepositoryImpl
import com.example.m_commerce.features.AddressMangment.domain.repository.AddressRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindShopifyAddressService(repo: AddressDataSourceImpl): AddressDataSource

    @Binds
    abstract fun bindAddressRepository(repo: AddressRepositoryImpl): AddressRepository



}