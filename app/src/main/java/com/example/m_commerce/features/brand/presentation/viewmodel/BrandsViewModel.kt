package com.example.m_commerce.features.brand.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.core.utils.NetworkManager
import com.example.m_commerce.features.brand.domain.usecases.GetBrandsUseCase
import com.example.m_commerce.features.brand.domain.usecases.GetProductsByBrandUseCase
import com.example.m_commerce.features.brand.presentation.ui_state.BrandsUiState
import com.example.m_commerce.features.product.presentation.ui_state.ProductsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrandsViewModel @Inject constructor(
    private val getBrandsUseCase: GetBrandsUseCase,
    private val getProductsUseCase: GetProductsByBrandUseCase,
    private val networkManager: NetworkManager,
) : ViewModel() {

    private val _brandsState: MutableStateFlow<BrandsUiState> =
        MutableStateFlow(BrandsUiState.Loading)
    val brandsState: StateFlow<BrandsUiState> = _brandsState.asStateFlow()

    private val _productsState: MutableStateFlow<ProductsUiState> =
        MutableStateFlow(ProductsUiState.Loading)
    val productsState: StateFlow<ProductsUiState> = _productsState.asStateFlow()


    init {
        if (!networkManager.isNetworkAvailable()) {
            _brandsState.tryEmit(BrandsUiState.NoNetwork)
        } else getBrandsData()

    }

    private fun getBrandsData() = viewModelScope.launch {
        try {
            val brands = getBrandsUseCase(30).catch { emit(null) }.firstOrNull()

            if (brands.isNullOrEmpty()) {
                _brandsState.value = BrandsUiState.Error("No Brands Found")
            } else {
                _brandsState.value = BrandsUiState.Success(brands)
            }
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
            _brandsState.value = BrandsUiState.Error(e.message.toString())
        }
    }

    fun getProductsByBrandName(name: String) = viewModelScope.launch {
        if (!networkManager.isNetworkAvailable()) {
            _productsState.emit(ProductsUiState.NoNetwork)
            return@launch
        }

        try {
            val products = getProductsUseCase(name).catch { emit(null) }.firstOrNull()
            if (products.isNullOrEmpty()) {
                _productsState.value = ProductsUiState.Error("No Products Found")
            } else {
                _productsState.value = ProductsUiState.Success(products)
            }
        } catch (e: Exception) {

        }
    }
}