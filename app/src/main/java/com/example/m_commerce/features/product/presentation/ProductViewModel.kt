package com.example.m_commerce.features.product.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.core.utils.NetworkManager
import com.example.m_commerce.features.product.domain.usecases.AddProductVariantToCart
import com.example.m_commerce.features.product.domain.usecases.AddToWishlistUseCase
import com.example.m_commerce.features.product.domain.usecases.CheckIfInWishlistUseCase
import com.example.m_commerce.features.product.domain.usecases.GetProductByIdUseCase
import com.example.m_commerce.features.wishlist.domain.usecases.DeleteFromWishlistUseCase
import com.shopify.buy3.Storefront
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val fetchProductById: GetProductByIdUseCase,
    private val addToWishlist: AddToWishlistUseCase,
    private val deleteFromWishlist: DeleteFromWishlistUseCase,
    private val checkIfInWishlist: CheckIfInWishlistUseCase,
    private val addProductVariantToCart: AddProductVariantToCart,
    private val networkManager: NetworkManager,
) : ViewModel() {

    private var _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _cartOperationResult = MutableSharedFlow<Boolean>()
    val cartOperationResult: SharedFlow<Boolean> = _cartOperationResult

    private var _message = MutableSharedFlow<SnackBarMessage>(replay = 0, extraBufferCapacity = 1)
    val message = _message.asSharedFlow()

    fun getProductById(productId: String) {

        viewModelScope.launch {
            if (!networkManager.isNetworkAvailable()) {
                _uiState.emit(ProductUiState.NoNetwork)
                return@launch
            }
            fetchProductById(productId)
                .catch {
                    _uiState.emit(
                        ProductUiState.Error(
                            it.message ?: "Unknown Error"
                        )
                    )
                }
                .collect { product ->
                    val isExist = checkIfInWishlist(product.id).first()
                    _uiState.emit(ProductUiState.Success(product, isFavorite = isExist))
                }
        }
    }

    fun findSelectedVariantId(
        variants: List<Storefront.ProductVariant>,
        selectedSize: String,
        selectedColor: String
    ): String? {
        return variants.find { variant ->
            val sizeMatches = if (selectedSize.isNotBlank()) {
                variant.selectedOptions.any { it.name == "Size" && it.value == selectedSize }
            } else true

            val colorMatches = if (selectedColor.isNotBlank()) {
                variant.selectedOptions.any { it.name == "Color" && it.value == selectedColor }
            } else true

            sizeMatches && colorMatches
        }?.id?.toString()
    }

    fun addProductToWishlist(productVariantId: String) = viewModelScope.launch {
        addToWishlist(productVariantId)
            .catch { _message.emit(SnackBarMessage("Failed to add product to wishlist: ${it.message}")) }
            .collect { _message.emit(SnackBarMessage(it)) }
    }

    fun deleteProductFromWishlist(productVariantId: String) = viewModelScope.launch {
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
                            }.collect()
                        }
                    }
                ))
            }
    }

    fun addToCart(productVariantId: String, quantity: Int) = viewModelScope.launch {
        if (!isConnected()) {
            _cartOperationResult.emit(false)
            return@launch
        }
        addProductVariantToCart(productVariantId, quantity)
            .catch {
                _message.emit(SnackBarMessage("Failed: ${it.message}"))
                _cartOperationResult.emit(false)
            }
            .collect {
                if (it) {
                    _message.emit(SnackBarMessage("Added to cart successfully"))
                }
                _cartOperationResult.emit(it)
            }
    }

    fun isConnected(): Boolean {
        return if (!networkManager.isNetworkAvailable()) {
            _message.tryEmit(SnackBarMessage("No internet connection"))
            false
        } else true
    }
}