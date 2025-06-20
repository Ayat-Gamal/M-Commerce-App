package com.example.m_commerce.features.brand.ui_state

import com.example.m_commerce.features.home.domain.entity.Brand

sealed class BrandsUiState {
    object Loading : BrandsUiState()
    data class Success(val brands: List<Brand>) : BrandsUiState()
    data class Error(val message: String) : BrandsUiState()
}