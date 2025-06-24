package com.example.m_commerce.features.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.brand.domain.usecases.GetBrandsUseCase
import com.example.m_commerce.features.brand.domain.usecases.GetProductsByBrandUseCase
import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.product.domain.usecases.GetProductByIdUseCase
import com.example.m_commerce.features.search.domain.usecases.GetProductsUseCase
import com.example.m_commerce.features.wishlist.domain.usecases.GetWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getWishlist: GetWishlistUseCase,
    private val getProductById: GetProductByIdUseCase,
    private val getProductsByBrand: GetProductsByBrandUseCase,
    private val getBrandsUse: GetBrandsUseCase,
    private val getProducts: GetProductsUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val uiState = _uiState.asStateFlow()
    private var products = emptyList<Product>()
    private var isFirst = true


    fun search(query: String) = viewModelScope.launch {
        _uiState.emit(SearchUiState.Loading)

        if (isFirst) {
            isFirst = false
            return@launch
        }

        val filteredProducts = products
            .filter {
                it.title.contains(query, true)
            }

        _uiState.emit(
            if (filteredProducts.isNotEmpty()) SearchUiState.Success(filteredProducts)
            else SearchUiState.Empty
        )
    }

    fun getAllProducts(fromWishlist: Boolean = false) {
        viewModelScope.launch {
            products = if (fromWishlist) {
                getWishlistProducts()
            } else {
                fetchAllProducts()
            }
            _uiState.emit(SearchUiState.Success(products))
        }
    }

    private suspend fun getWishlistProducts(): List<Product> {
        return getWishlist() // List<String>
            .flatMapConcat { ids ->
                if (ids.isEmpty()) {
                    flowOf(emptyList()) // List<Product>
                } else {
                    ids
                        .asFlow() // Flow<String>
                        .flatMapMerge { id ->
                            getProductById(id) // Flow<Product>
                        }
                        .toList()   // Flow<List<Product>>
                        .let { productsList -> flowOf(productsList) }
                }
            }
            .catch { e -> _uiState.emit(SearchUiState.Error(e.message ?: "Unknown error")) }
            .first()
    }

    private suspend fun fetchAllProducts() = getProducts().first()
}