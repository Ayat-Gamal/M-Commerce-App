package com.example.m_commerce.features.home.presentation.ui_state

import com.example.m_commerce.features.brand.domain.entity.Brand
import com.example.m_commerce.features.categories.domain.entity.Category
import com.example.m_commerce.features.coupon.domain.entity.Coupon

sealed class HomeUiState {
    object Loading : HomeUiState()
    object Search : HomeUiState()
    object NoNetwork : HomeUiState()
    data class Success(val brands: List<Brand>, val couponCodes: List<Coupon> ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}