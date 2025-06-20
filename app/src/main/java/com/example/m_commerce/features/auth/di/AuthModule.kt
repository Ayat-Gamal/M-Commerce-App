package com.example.m_commerce.features.auth.di

import android.content.Context
import com.example.m_commerce.features.auth.domain.repo.AuthRepository
import com.example.m_commerce.features.auth.domain.usecases.CreateCustomerTokenUseCase
import com.example.m_commerce.features.auth.domain.usecases.LoginUserUseCase
import com.example.m_commerce.features.auth.domain.usecases.RegisterUserUseCase
import com.example.m_commerce.features.auth.domain.usecases.SendEmailVerificationUseCase
import com.google.firebase.auth.FirebaseAuth
import com.shopify.buy3.GraphClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideRegisterUserUseCase(repo: AuthRepository): RegisterUserUseCase {
        return RegisterUserUseCase(repo)
    }

    @Provides
    fun provideSendEmailVerificationUseCase(repo: AuthRepository): SendEmailVerificationUseCase {
        return SendEmailVerificationUseCase(repo)
    }

    @Provides
    fun provideLoginUserUseCase(repo: AuthRepository): LoginUserUseCase {
        return LoginUserUseCase(repo)
    }

    @Provides
    fun provideCreateCustomerTokenUseCase(repo: AuthRepository): CreateCustomerTokenUseCase {
        return CreateCustomerTokenUseCase(repo)
    }

    @Provides
    fun provideGraphClient(@ApplicationContext context: Context): GraphClient {
        return GraphClient.build(
            context = context,
            shopDomain = "mad45-alex-and02.myshopify.com",
            accessToken = "cf0390c1a174351fc5092b6f62d71a32"
        )

    }
}