package com.example.m_commerce.features.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.core.utils.NetworkManager
import com.example.m_commerce.features.AddressMangment.domain.usecases.GetCustomerNameUseCase
import com.example.m_commerce.features.brand.domain.usecases.GetBrandsUseCase
import com.example.m_commerce.features.categories.domain.usecases.GetSubCategoriesUseCase
import com.example.m_commerce.features.coupon.domain.usecases.GetCouponsUseCase
import com.example.m_commerce.features.home.presentation.ui_state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBrandsUseCase: GetBrandsUseCase,
    private val getCouponsUseCase: GetCouponsUseCase,
    private val getSubCategoriesUseCase: GetSubCategoriesUseCase,
    private val networkManager: NetworkManager,
    private val getCustomerName: GetCustomerNameUseCase,
) : ViewModel() {

    private val _dataState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val dataState: StateFlow<HomeUiState> = _dataState.asStateFlow()


    fun getHomeData() = viewModelScope.launch {
        if (!networkManager.isNetworkAvailable()) {
            _dataState.emit(HomeUiState.NoNetwork)
            return@launch
        }
            _dataState.emit(HomeUiState.Loading)

        try {
            val coupons = getCouponsUseCase()
                .catch {
                    emit(emptyList())
                }
                .firstOrNull() ?: emptyList()


            val brands = getBrandsUseCase(50).catch { emit(null) }.firstOrNull()
            val categories = getSubCategoriesUseCase(Unit).catch { emit(null) }.firstOrNull()
            val name  = getCustomerName().catch {  }.firstOrNull()

            if (brands.isNullOrEmpty()) {
                _dataState.value = HomeUiState.Error("No Brands Found")
            } else if (categories.isNullOrEmpty()) {
                _dataState.value = HomeUiState.Error("No Categories Found")
            } else {
                _dataState.value = HomeUiState.Success(brands, categories, coupons, name ?: "Guest")
            }
        } catch (e: Exception) {
            Log.i("Error", e.message.toString())
            _dataState.value = HomeUiState.Error(e.message.toString())
        }
    }



}