package com.example.m_commerce.features.cart.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.cart.domain.usecases.GetCartByIdUseCase
import com.example.m_commerce.features.cart.domain.usecases.RemoveProductVariantUseCase
import com.example.m_commerce.features.cart.domain.usecases.UpdateCartUseCase
import com.example.m_commerce.features.cart.presentation.CartUiState
import com.example.m_commerce.features.coupon.domain.usecases.ApplyCouponUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartByIdUseCase: GetCartByIdUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val removeProductVariantUseCase: RemoveProductVariantUseCase,
    private val applyCouponUseCase: ApplyCouponUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getCartById() = viewModelScope.launch {

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            _uiState.value = CartUiState.Guest
        }

        try {
            getCartByIdUseCase.invoke("cartId").collect { cart ->
                when {
                    cart.lines.isEmpty() -> _uiState.value = CartUiState.Empty
                    else -> {
                        Log.i("TAG", "Cart details: ${cart.lines}")
                        _uiState.value = CartUiState.Success(cart)
                    }
                }
            }
        } catch (e: Exception) {
            _uiState.value = when {
                e.message?.contains("guest") == true -> CartUiState.Guest
                e.message?.contains("network", ignoreCase = true) == true -> CartUiState.NoNetwork
                else -> CartUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun increaseQuantity(lineId: String) = viewModelScope.launch {
        try {
            val currentState = _uiState.value
            if (currentState is CartUiState.Success) {
                val line = currentState.cart.lines.find { it.lineId == lineId }
                line?.let {
                    val newQuantity = it.quantity + 1
                    updateLineQuantity(lineId, newQuantity)
                }
            }
        } catch (e: Exception) {
            Log.i("TAG", "increaseQuantity: ${e.message}  ")
            _uiState.value = CartUiState.Error(e.message ?: "Increase failed")
        }
    }

    fun decreaseQuantity(lineId: String) = viewModelScope.launch {
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

    fun applyCoupon(couponCode: String) = viewModelScope.launch {
        try {
            applyCouponUseCase(couponCode).collect { success ->
                if (success) {
                    getCartById()
                } else {
                    _uiState.value = CartUiState.Error("Failed to apply coupon")
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


}

