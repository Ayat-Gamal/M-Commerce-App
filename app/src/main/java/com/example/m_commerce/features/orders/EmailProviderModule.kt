package com.example.m_commerce.features.orders

import com.example.m_commerce.features.orders.data.datasources.remote.service.EmailJsService
import com.example.m_commerce.features.orders.data.repository.EmailJsRepositoryImpl
import com.example.m_commerce.features.orders.domain.repository.EmailJsRepository
import com.example.m_commerce.features.orders.domain.usecases.SendOrderConfirmationEmailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EmailProviderModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.emailjs.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideEmailJsApi(retrofit: Retrofit): EmailJsService {
        return retrofit.create(EmailJsService::class.java)
    }

    @Provides
    fun provideEmailRepository(api: EmailJsService): EmailJsRepository {
        return EmailJsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSendEmailUseCase(repo: EmailJsRepository): SendOrderConfirmationEmailUseCase {
        return SendOrderConfirmationEmailUseCase(repo)
    }


}