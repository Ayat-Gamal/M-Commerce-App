package com.example.m_commerce.features.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.core.utils.NetworkManager
import com.example.m_commerce.features.brand.domain.usecases.GetBrandsUseCase
import com.example.m_commerce.features.categories.domain.usecases.GetSubCategoriesUseCase
import com.example.m_commerce.features.coupon.domain.usecases.GetCouponsUseCase
import com.example.m_commerce.features.home.presentation.ui_state.HomeUiState
import com.example.m_commerce.features.product.presentation.ProductUiState
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
    private val getCouponsUseCase: GetCouponsUseCase,
    private val networkManager: NetworkManager,
    ) : ViewModel() {

    private val _dataState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val dataState: StateFlow<HomeUiState> = _dataState.asStateFlow()


    fun getHomeData() = viewModelScope.launch {
        if (!networkManager.isNetworkAvailable()) {
            _dataState.emit(HomeUiState.NoNetwork)
            return@launch
        }

        try {
            val coupons = getCouponsUseCase()
                .catch { emit(emptyList()) }
                .firstOrNull() ?: emptyList()

           // val couponCodes = coupons.map { it }

            val brands = getBrandsUseCase(50).catch { emit(null) }.firstOrNull()

            //TODO: This might be a bad idea
            if (brands.isNullOrEmpty()) {
                _dataState.value = HomeUiState.Error("No Brands Found")
            } else {
                _dataState.value = HomeUiState.Success(brands, coupons)
            }
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
            _dataState.value = HomeUiState.Error(e.message.toString())
        }
    }

}