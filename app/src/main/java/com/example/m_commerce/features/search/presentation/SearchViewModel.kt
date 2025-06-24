package com.example.m_commerce.features.search.presentation

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.brand.domain.usecases.GetBrandsUseCase
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
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getWishlist: GetWishlistUseCase,
    private val getProductById: GetProductByIdUseCase,
    private val getBrandsUseCase: GetBrandsUseCase,
    private val getProducts: GetProductsUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val uiState = _uiState.asStateFlow()
    private var products = emptyList<Product>()
    private var filteredProducts = emptyList<Product>()
    private var isFirst = true

    private var _brands = mutableListOf<String>()
    var brands: List<String> = _brands

    var colors = mutableListOf<String>()

    init {
        getBrands()
    }

    fun search(query: String) = viewModelScope.launch {
        _uiState.emit(SearchUiState.Loading)

        if (isFirst) {
            isFirst = false
            return@launch
        }

        filteredProducts = products
            .filter {
                it.title.contains(query, true)
            }

        _uiState.emit(
            if (filteredProducts.isNotEmpty()) SearchUiState.Success(filteredProducts)
            else SearchUiState.Empty
        )
    }

    fun filter(selectedFilters: SnapshotStateMap<String, String>) {
        filteredProducts = products.filter { product ->
            selectedFilters.all { (key, value) ->
                when (key) {
                    "Color" -> product.colors.contains(value.lowercase())
                    "Category" -> product.category.equals(value, true)
                    "Brand" -> product.brand.equals(value, true)
                    else -> true
                }
            }
        }

        _uiState.value = if (filteredProducts.isEmpty()) {
            SearchUiState.Empty
        } else {
            SearchUiState.Success(filteredProducts)
        }
    }

    fun getAllProducts(fromWishlist: Boolean = false) {
        viewModelScope.launch {
            products = if (fromWishlist) {
                getWishlistProducts()
            } else {
                fetchAllProducts()
            }
            colors = products
                .flatMap { it.colors }
                .toSet()
                .toList().toMutableList()
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

    private fun getBrands() = viewModelScope.launch {
        getBrandsUseCase(50)
            .mapNotNull { brands -> brands?.mapNotNull { it.name }?.drop(1) }
            .collect { brandNames ->
                _brands.addAll(brandNames)
            }
    }
}