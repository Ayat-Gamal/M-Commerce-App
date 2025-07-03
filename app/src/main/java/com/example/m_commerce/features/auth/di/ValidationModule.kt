package com.example.m_commerce.features.auth.di

import com.example.m_commerce.features.auth.domain.validation.ValidateConfirmPassword
import com.example.m_commerce.features.auth.domain.validation.ValidateEmail
import com.example.m_commerce.features.auth.domain.validation.ValidateName
import com.example.m_commerce.features.auth.domain.validation.ValidatePassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ValidationModule {
    @Provides
    fun provideValidateConfirmPassword() = ValidateConfirmPassword()

    @Provides
    fun provideValidatePassword() = ValidatePassword()

    @Provides
    fun provideValidateName() = ValidateName()

    @Provides
    fun provideValidateEmail() = ValidateEmail()
}