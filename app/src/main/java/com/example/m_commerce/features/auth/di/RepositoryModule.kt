package com.example.m_commerce.features.auth.di

import com.example.m_commerce.features.auth.data.remote.UsersRemoteDataSource
import com.example.m_commerce.features.auth.data.remote.UsersRemoteDataSourceImpl
import com.example.m_commerce.features.auth.data.repo.AuthRepositoryImpl
import com.example.m_commerce.features.auth.domain.repo.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindUsersRemoteDataSource(impl: UsersRemoteDataSourceImpl): UsersRemoteDataSource
}