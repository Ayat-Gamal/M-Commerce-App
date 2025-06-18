package com.example.m_commerce.features.auth.di

import com.example.m_commerce.features.auth.domain.repo.AuthRepository
import com.example.m_commerce.features.auth.domain.usecases.LoginUserUseCase
import com.example.m_commerce.features.auth.domain.usecases.RegisterUserUseCase
import com.example.m_commerce.features.auth.domain.usecases.SendEmailVerificationUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}