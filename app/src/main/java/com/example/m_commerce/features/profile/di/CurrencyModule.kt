package com.example.m_commerce.features.profile.di


import com.example.m_commerce.features.profile.data.reposetoryImpl.CurrencyRepositoryImpl
import com.example.m_commerce.features.profile.data.service.CurrencyApiService
import com.example.m_commerce.features.profile.domain.repository.CurrencyRepository
import com.example.m_commerce.features.profile.domain.usecase.GetCurrenciesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyModule {

    @Provides
    @Singleton
    fun provideCurrencyApi(): CurrencyApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.currencyfreaks.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(api: CurrencyApiService): CurrencyRepository {
        return CurrencyRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetCurrenciesUseCase(repository: CurrencyRepository): GetCurrenciesUseCase {
        return GetCurrenciesUseCase(repository)
    }
}
