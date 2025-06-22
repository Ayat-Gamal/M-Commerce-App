package com.example.m_commerce.features.orders

import com.example.m_commerce.features.orders.data.datasources.remote.OrderEmailService
import com.example.m_commerce.features.orders.data.datasources.remote.OrderEmailServiceImpl
import com.example.m_commerce.features.orders.data.repository.EmailRepositoryImpl
import com.example.m_commerce.features.orders.domain.repository.EmailRepository
import com.example.m_commerce.features.orders.domain.usecases.SendOrderConfirmationEmailUseCase
import com.google.firebase.firestore.FirebaseFirestore
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

//    @Provides
//    fun provideRetrofit(): Retrofit = Retrofit.Builder()
//        .baseUrl("https://api.mailersend.com/v1/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    @Provides
//    fun provideMailerSendService(retrofit: Retrofit): MailerService =
//        retrofit.create(MailerService::class.java)

    @Provides
    fun provideOrderEmailService(fireStore: FirebaseFirestore): OrderEmailService {
        return OrderEmailServiceImpl(fireStore)
    }
    @Provides
    fun provideEmailRepository(api: OrderEmailService): EmailRepository {
        return EmailRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSendEmailUseCase(repo: EmailRepository): SendOrderConfirmationEmailUseCase {
        return SendOrderConfirmationEmailUseCase(repo)
    }


}