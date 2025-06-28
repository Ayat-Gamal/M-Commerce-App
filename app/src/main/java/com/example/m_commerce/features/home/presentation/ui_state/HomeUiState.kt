package com.example.m_commerce.features.home.presentation.ui_state

import com.example.m_commerce.features.brand.domain.entity.Brand
import com.example.m_commerce.features.categories.domain.entity.Category
import com.example.m_commerce.features.coupon.domain.entity.Coupon
import com.google.firebase.auth.FirebaseUser

sealed class HomeUiState {
    object Loading : HomeUiState()
    object NoNetwork : HomeUiState()
    data class Success(val brands: List<Brand>, val categories: List<Category>, val couponCodes: List<Coupon>, val userName: String) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}