package com.example.m_commerce.features.brand.ui_state

import com.example.m_commerce.features.brand.domain.entity.Brand
import com.example.m_commerce.features.categories.domain.entity.Category

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val brands: List<Brand>, val categories: List<Category>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}