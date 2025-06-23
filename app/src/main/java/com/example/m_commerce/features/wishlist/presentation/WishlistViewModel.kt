package com.example.m_commerce.features.wishlist.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.product.domain.usecases.GetProductByIdUseCase
import com.example.m_commerce.features.wishlist.domain.usecases.DeleteFromWishlistUseCase
import com.example.m_commerce.features.wishlist.domain.usecases.GetWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val getWishlist: GetWishlistUseCase,
    private val getProductById: GetProductByIdUseCase,
    private val deleteFromWishlist: DeleteFromWishlistUseCase,
) : ViewModel() {
    private var _uiState = MutableStateFlow<WishlistUiState>(WishlistUiState.Loading)
    val uiState = _uiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getProducts() {
        viewModelScope.launch {
            getWishlist()
                .flatMapConcat { ids ->
                    Log.d("TAG", "getProducts: ${ids.size}")
                    if (ids.isEmpty()) {
                        flowOf(emptyList())
                    } else {
                        ids
                            .asFlow()
                            .flatMapMerge { id ->
                                getProductById(id)
                            }
                            .toList()
                            .let { productsList -> flowOf(productsList) }
                    }
                }
                .catch { e -> _uiState.emit(WishlistUiState.Error(e.message ?: "Unknown error")) }
                .collect { products ->
                    if (products.isEmpty()) _uiState.emit(WishlistUiState.Empty)
                    else _uiState.emit(WishlistUiState.Success(products))
                }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            if (query.isNotEmpty())
                _uiState.emit(WishlistUiState.Search)
            else {
                _uiState.emit(WishlistUiState.Loading)
                getProducts()
            }
        }
    }

    fun deleteProductFromWishlist(productVariantId: String) = viewModelScope.launch {
        deleteFromWishlist(productVariantId)
    }
}