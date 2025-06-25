package com.example.m_commerce.features.profile.di


import android.content.Context
import android.content.SharedPreferences
import com.example.m_commerce.features.profile.data.local.CurrencyPreferencesDataSource
import com.example.m_commerce.features.profile.data.remote.CurrencyApiService
import com.example.m_commerce.features.profile.data.reposetoryImpl.CurrencyRepositoryImpl
import com.example.m_commerce.features.profile.domain.repository.CurrencyRepository
import com.example.m_commerce.features.profile.domain.usecase.GetCurrenciesUseCase
import com.example.m_commerce.features.profile.domain.usecase.GetDefaultCurrencyUseCase
import com.example.m_commerce.features.profile.domain.usecase.GetExchangeRateUseCase
import com.example.m_commerce.features.profile.domain.usecase.SaveDefaultCurrencyUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyModule {

    @Provides
    @Singleton
    fun provideCurrencyApiService(): CurrencyApiService =
        Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/v6/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApiService::class.java)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("currency_prefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideCurrencyPreferencesDataSource(prefs: SharedPreferences): CurrencyPreferencesDataSource =
        CurrencyPreferencesDataSource(prefs)

    @Provides
    @Singleton
    fun provideCurrencyRepository(
        local: CurrencyPreferencesDataSource,
        remote: CurrencyApiService
    ): CurrencyRepository = CurrencyRepositoryImpl(local, remote)

    @Provides
    @Singleton
    fun provideGetCurrenciesUseCase(repo: CurrencyRepository): GetCurrenciesUseCase =
        GetCurrenciesUseCase(repo)

    @Provides
    @Singleton
    fun provideGetExchangeRateUseCase(repo: CurrencyRepository): GetExchangeRateUseCase =
        GetExchangeRateUseCase(repo)

    @Provides
    @Singleton
    fun provideGetDefaultCurrencyUseCase(repo: CurrencyRepository): GetDefaultCurrencyUseCase =
        GetDefaultCurrencyUseCase(repo)

    @Provides
    @Singleton
    fun provideSaveDefaultCurrencyUseCase(repo: CurrencyRepository): SaveDefaultCurrencyUseCase =
        SaveDefaultCurrencyUseCase(repo)

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}
