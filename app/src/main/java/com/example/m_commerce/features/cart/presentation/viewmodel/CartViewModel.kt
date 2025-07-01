package com.example.m_commerce.features.cart.presentation.viewmodel

import ProductVariant
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.core.utils.NetworkManager
import com.example.m_commerce.features.cart.domain.usecases.GetCartByIdUseCase
import com.example.m_commerce.features.cart.domain.usecases.RemoveProductVariantUseCase
import com.example.m_commerce.features.cart.domain.usecases.UpdateCartUseCase
import com.example.m_commerce.features.cart.presentation.CartUiState
import com.example.m_commerce.features.cart.presentation.UiEvent
import com.example.m_commerce.features.coupon.domain.usecases.ApplyCouponUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartByIdUseCase: GetCartByIdUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val removeProductVariantUseCase: RemoveProductVariantUseCase,
    private val applyCouponUseCase: ApplyCouponUseCase,
    private val networkManager: NetworkManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _snackBarFlow = MutableSharedFlow<UiEvent>(replay = 0, extraBufferCapacity = 1)
    val snackBarFlow = _snackBarFlow.asSharedFlow()


//    init {
//        if (!networkManager.isNetworkAvailable()) {
//            _uiState.tryEmit(CartUiState.NoNetwork)
//        } else {
//            if (FirebaseAuth.getInstance().currentUser == null) {
//                _uiState.tryEmit(CartUiState.Guest)
//            } else getCartById()
//        }
//    }

    fun getCart() {
        if (!networkManager.isNetworkAvailable()) {
            _uiState.tryEmit(CartUiState.NoNetwork)
        } else {
            if (FirebaseAuth.getInstance().currentUser == null) {
                _uiState.tryEmit(CartUiState.Guest)
            } else getCartById()
        }
    }

    fun getCartById() = viewModelScope.launch {

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            _uiState.value = CartUiState.Guest
        }

        try {
            getCartByIdUseCase.invoke().collect { cart ->
                when {
                    cart.lines.isEmpty() -> _uiState.value = CartUiState.Empty
                    else -> {
                        Log.i("TAG", "Cart details: ${cart.lines}")
                        _uiState.value = CartUiState.Success(cart)
                    }
                }
            }
        } catch (e: Exception) {
            Log.i("TAG", "yes getCartById: ")
            _uiState.value = when {
                e.message?.contains("guest") == true -> CartUiState.Guest
                e.message?.contains("network", ignoreCase = true) == true -> CartUiState.NoNetwork
                else -> CartUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun increaseQuantity(lineId: String) = viewModelScope.launch {
        if (!isConnected()) return@launch
        try {
            val currentState = _uiState.value
            if (currentState is CartUiState.Success) {
                val line = currentState.cart.lines.find { it.lineId == lineId }
                line?.let {
                    val newQuantity = it.quantity + 1
                    if (newQuantity <= it.availableQuantity) {
                        updateLineQuantity(lineId, newQuantity)
                    } else {
                        viewModelScope.launch {
                            _snackBarFlow.emit(UiEvent.ShowSnackbar("Maximum quantity reached"))
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.i("TAG", "increaseQuantity: ${e.message}  ")
            _uiState.value = CartUiState.Error(e.message ?: "Increase failed")
        }
    }

    fun decreaseQuantity(lineId: String) = viewModelScope.launch {
        if (!isConnected()) return@launch
        try {
            val currentState = _uiState.value
            if (currentState is CartUiState.Success) {
                val line = currentState.cart.lines.find { it.lineId == lineId }
                line?.let {
                    val newQuantity = maxOf(1, it.quantity - 1)
                    if (newQuantity != it.quantity) {
                        updateLineQuantity(lineId, newQuantity)
                    }
                }
            }
        } catch (e: Exception) {
            _uiState.value = CartUiState.Error(e.message ?: "Decrease failed")
        }
    }

    fun removeLine(cartLineId: String) = viewModelScope.launch {
        try {
            removeProductVariantUseCase(cartLineId).collect { success ->
                if (success) {
                    getCartById()
                } else {
                    _uiState.value = CartUiState.Error("Failed to remove item")
                }
            }
        } catch (e: Exception) {
            _uiState.value = CartUiState.Error(e.message ?: "Remove failed")
        }
    }

    fun clearCart(items: List<ProductVariant>) = viewModelScope.launch {
        if (!isConnected()) return@launch
        try {
            for (item in items) {
                val success = removeProductVariantUseCase(item.lineId).first()
                if (!success) {
                    _uiState.value = CartUiState.Error("Failed to remove item: ${item.lineId}")
                    return@launch
                }
            }
            getCartById()
        } catch (e: Exception) {
            _uiState.value = CartUiState.Error(e.message ?: "Clear failed")
        }
    }

    fun applyCoupon(couponCode: String) = viewModelScope.launch {
        if (!isConnected()) return@launch
        try {
            applyCouponUseCase(couponCode).collect { success ->
                if (success) {
                    getCartById()
                } else {
                    viewModelScope.launch {
                        _snackBarFlow.emit(UiEvent.ShowSnackbar("Failed to apply coupon"))
                    }
                }
            }
        } catch (e: Exception) {
            _uiState.value = CartUiState.Error(e.message ?: "Apply failed")
        }
    }

    private suspend fun updateLineQuantity(lineId: String, newQuantity: Int) {
        try {
            updateCartUseCase.invoke(productVariantId = lineId, quantity = newQuantity)
                .collect { success ->
                    if (success) {
                        getCartById()
                    } else {
                        _uiState.value = CartUiState.Error("Update failed")
                    }
                }
        } catch (e: Exception) {
            _uiState.value = CartUiState.Error(e.message ?: "Update failed")
        }
    }

     fun isConnected(): Boolean {
        return if (!networkManager.isNetworkAvailable()) {
            _snackBarFlow.tryEmit(UiEvent.ShowSnackbar("No internet connection"))
            false
        } else true
    }
}

