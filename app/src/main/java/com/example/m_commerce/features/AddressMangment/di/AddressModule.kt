package com.example.m_commerce.features.AddressMangment.di

import com.example.m_commerce.features.AddressMangment.domain.repository.AddressRepository
import com.example.m_commerce.features.AddressMangment.domain.usecases.GetAddressesUseCase
import com.example.m_commerce.features.AddressMangment.domain.usecases.GetDefaultAddressUseCase
import com.example.m_commerce.features.AddressMangment.domain.usecases.SaveAddressUseCase
import com.example.m_commerce.features.AddressMangment.domain.usecases.SetDefaultAddressUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    fun provideSaveAddressUseCase(repo: AddressRepository) = SaveAddressUseCase(repo)

    @Provides
    fun provideGetCustomerAddressesUseCase(repo: AddressRepository) = GetAddressesUseCase(repo)

    @Provides
    fun provideSetDefaultAddressUseCase(repo: AddressRepository) = SetDefaultAddressUseCase(repo)

    @Provides
    fun provideGetDefaultAddressUseCase(repo: AddressRepository) = GetDefaultAddressUseCase(repo)
}