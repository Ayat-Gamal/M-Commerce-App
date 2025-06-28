package com.example.m_commerce.features.auth.domain.usecases

import com.example.m_commerce.features.auth.domain.repo.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateCartUseCase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke(token: String): Flow<String> {
        return repo.createCart(token)
    }
}