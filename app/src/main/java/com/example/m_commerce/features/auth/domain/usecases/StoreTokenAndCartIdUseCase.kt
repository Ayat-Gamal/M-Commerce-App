package com.example.m_commerce.features.auth.domain.usecases

import com.example.m_commerce.features.auth.domain.repo.AuthRepository
import javax.inject.Inject

class StoreTokenAndCartIdUseCase @Inject constructor(private val repo: AuthRepository) {
    suspend operator fun invoke(token: String, cartId: String, uid: String) {
        return repo.storeTokenAndCartId(token, cartId, uid)
    }
}