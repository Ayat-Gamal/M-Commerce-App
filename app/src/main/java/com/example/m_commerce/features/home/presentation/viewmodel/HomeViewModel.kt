package com.example.m_commerce.features.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.brand.domain.usecases.GetBrandsUseCase
import com.example.m_commerce.features.categories.domain.usecases.GetCategoriesUseCase
import com.example.m_commerce.features.coupon.domain.usecases.GetCouponsUseCase
import com.example.m_commerce.features.home.presentation.ui_state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBrandsUseCase: GetBrandsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getCouponsUseCase: GetCouponsUseCase
) : ViewModel() {

    private val _dataState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val dataState: StateFlow<HomeUiState> = _dataState.asStateFlow()


    fun getHomeData() = viewModelScope.launch {

        try {
            val coupons = getCouponsUseCase()
                .catch { emit(emptyList()) }
                .firstOrNull() ?: emptyList()

           // val couponCodes = coupons.map { it }

            val brands = getBrandsUseCase(7).catch { emit(null) }.firstOrNull()
            val categories = getCategoriesUseCase(Unit).catch { emit(null) }.firstOrNull()

            //TODO: This might be a bad idea
            if (brands.isNullOrEmpty()) {
                _dataState.value = HomeUiState.Error("No Brands Found")
            } else if (categories.isNullOrEmpty()) {
                _dataState.value = HomeUiState.Error("No Categories Found")
            } else {
                _dataState.value = HomeUiState.Success(brands, categories , coupons)
            }
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
            _dataState.value = HomeUiState.Error(e.message.toString())
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            if (query.isNotEmpty())
                _dataState.emit(HomeUiState.Search)
            else {
                _dataState.emit(HomeUiState.Loading)
                getHomeData()
            }
        }
    }



}