package com.example.m_commerce.features.wishlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.core.utils.NetworkManager
import com.example.m_commerce.features.product.domain.usecases.AddToWishlistUseCase
import com.example.m_commerce.features.product.domain.usecases.GetProductByIdUseCase
import com.example.m_commerce.features.product.presentation.SnackBarMessage
import com.example.m_commerce.features.wishlist.domain.usecases.DeleteFromWishlistUseCase
import com.example.m_commerce.features.wishlist.domain.usecases.GetWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    private val addToWishlist: AddToWishlistUseCase,
    private val networkManager: NetworkManager,
) : ViewModel() {
    private var _uiState = MutableStateFlow<WishlistUiState>(WishlistUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var _message = MutableSharedFlow<SnackBarMessage>()
    val message = _message.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getProducts() {
        if (!networkManager.isNetworkAvailable()) {
            _uiState.tryEmit(WishlistUiState.NoNetwork)
            return
        }
        viewModelScope.launch {
            getWishlist() // p1, p2
                .flatMapConcat { ids ->
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
                    val sortedProducts = products.sortedBy { it.id }
                    if (sortedProducts.isEmpty()) _uiState.emit(WishlistUiState.Empty)
                    else _uiState.emit(WishlistUiState.Success(sortedProducts))
                }
        }
    }

    fun deleteProductFromWishlist(productVariantId: String) = viewModelScope.launch {
        if (!networkManager.isNetworkAvailable()) {
            _message.emit(SnackBarMessage("No internet connection"))
            return@launch
        }
        deleteFromWishlist(productVariantId)
            .catch { _message.emit(SnackBarMessage("Failed to remove product from wishlist: ${it.message}")) }
            .collect {
                _message.emit(SnackBarMessage(
                    message = it,
                    actionLabel = "Undo",
                    onAction = {
                        viewModelScope.launch {
                            addToWishlist(productVariantId).catch { e ->
                                _message.emit(SnackBarMessage("Failed to add product to wishlist: ${e.message}"))
                            }.collect {
                                getProducts()
                            }
                        }
                    }
                ))
            }
        getProducts()
    }
}