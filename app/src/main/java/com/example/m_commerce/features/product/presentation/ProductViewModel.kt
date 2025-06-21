package com.example.m_commerce.features.product.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.product.domain.usecases.AddToWishlistUseCase
import com.example.m_commerce.features.product.domain.usecases.CheckIfInWishlistUseCase
import com.example.m_commerce.features.product.domain.usecases.GetProductByIdUseCase
import com.example.m_commerce.features.wishlist.domain.usecases.DeleteFromWishlistUseCase
import com.shopify.buy3.Storefront
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val fetchProductById: GetProductByIdUseCase,
    private val addToWishlist: AddToWishlistUseCase,
    private val deleteFromWishlist: DeleteFromWishlistUseCase,
    private val checkIfInWishlist: CheckIfInWishlistUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getProductById(productId: String) {
        viewModelScope.launch {
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
            val hasSize =
                variant.selectedOptions.any { it.name == "Size" && it.value == selectedSize }
            val hasColor =
                variant.selectedOptions.any { it.name == "Color" && it.value == selectedColor }
            hasSize && hasColor
        }?.id?.toString()
    }

    fun addProductToWishlist(productVariantId: String) = viewModelScope.launch {
        addToWishlist(productVariantId)
    }

    fun deleteProductFromWishlist(productVariantId: String) = viewModelScope.launch {
        deleteFromWishlist(productVariantId)
    }
}