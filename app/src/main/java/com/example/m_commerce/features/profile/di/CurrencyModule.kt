package com.example.m_commerce.features.profile.di


import android.content.Context
import android.content.SharedPreferences
import com.example.m_commerce.features.profile.data.reposetoryImpl.CurrencyPreferencesRepositoryImpl
import com.example.m_commerce.features.profile.data.reposetoryImpl.CurrencyRepositoryImpl
import com.example.m_commerce.features.profile.data.service.CurrencyApiService
import com.example.m_commerce.features.profile.domain.repository.CurrencyPreferencesRepository
import com.example.m_commerce.features.profile.domain.repository.CurrencyRepository
import com.example.m_commerce.features.profile.domain.usecase.GetCurrenciesUseCase
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

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("currency_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()


    @Provides
    @Singleton
    fun provideCurrencyPreferencesRepository(
        sharedPreferences: SharedPreferences,
        gson: Gson
    ): CurrencyPreferencesRepository {
        return CurrencyPreferencesRepositoryImpl(sharedPreferences, gson)
    }

}
