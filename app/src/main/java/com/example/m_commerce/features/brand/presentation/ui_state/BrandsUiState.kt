package com.example.m_commerce.features.brand.presentation.ui_state

import com.example.m_commerce.features.brand.domain.entity.Brand
import com.example.m_commerce.features.categories.domain.entity.Category


sealed class BrandsUiState {
    object Loading : BrandsUiState()
    object NoNetwork : BrandsUiState()
    data class Success(val brands: List<Brand>, val categories: List<Category>?) : BrandsUiState()
    data class Error(val message: String) : BrandsUiState()
}