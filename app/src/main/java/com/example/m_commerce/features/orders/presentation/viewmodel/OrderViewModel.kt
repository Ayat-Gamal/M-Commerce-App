package com.example.m_commerce.features.orders.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.BuildConfig
import com.example.m_commerce.features.orders.data.model.CompleteOrderVariables
import com.example.m_commerce.features.orders.data.model.DraftOrderCreateVariables
import com.example.m_commerce.features.orders.data.model.GraphQLRequest
import com.example.m_commerce.features.orders.data.model.LineItem
import com.example.m_commerce.features.orders.data.model.ShippingAddress
import com.example.m_commerce.features.orders.data.model.completeDraftOrderQuery
import com.example.m_commerce.features.orders.data.model.createDraftOrderQuery
import com.example.m_commerce.features.orders.domain.usecases.CreateOrderUseCase
import com.example.m_commerce.features.orders.presentation.ui_state.OrderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class OrderViewModel @Inject constructor(private val createOrderUseCase: CreateOrderUseCase, /*private val completeOrderUseCase: CompleteOrderUseCase*/) : ViewModel() {

    private val _state = MutableStateFlow<OrderUiState>(OrderUiState.Idle)
    val state: StateFlow<OrderUiState> = _state

    fun completeOrder() = viewModelScope.launch {

    }

    fun createOrder() {
        val variables = DraftOrderCreateVariables(
            email = "youssifn.mostafa@gmail.com",
            shippingAddress = ShippingAddress(
                firstName = "Youssif",
                lastName = "Nasser",
                address1 = "123 Main Street",
                city = "Cairo",
                country = "Egypt",
                zip = "12345"
            ),
            lineItems = listOf( //!
                LineItem(
                    variantId = "gid://shopify/ProductVariant/46559952797945",
                    quantity = 2,
                    originalUnitPrice = "150.00"
                )
            ),
            note = null
        )

        val request = GraphQLRequest(
            query = createDraftOrderQuery,
            variables = variables
        )

        viewModelScope.launch {
            _state.value = OrderUiState.Loading
            try {
                createOrderUseCase(request).collect { order ->
                    Log.d("Shopify", "createOrder: ${order}")
                    _state.value = OrderUiState.Success(order)
                }
            } catch (e: Exception) {
                _state.value = OrderUiState.Error(e.message ?: "Unknown error")
                Log.e("Shopify", "createOrder:", e )
            }
        }
    }
}