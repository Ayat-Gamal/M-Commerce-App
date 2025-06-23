package com.example.m_commerce.features.orders.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.orders.data.PaymentMethod
import com.example.m_commerce.features.orders.data.model.variables.DraftOrderCreateVariables
import com.example.m_commerce.features.orders.data.model.GraphQLRequest
import com.example.m_commerce.features.orders.data.model.variables.LineItem
import com.example.m_commerce.features.orders.data.model.variables.ShippingAddress
import com.example.m_commerce.features.orders.data.model.completeDraftOrderQuery
import com.example.m_commerce.features.orders.data.model.createDraftOrderQuery
import com.example.m_commerce.features.orders.data.model.variables.CompleteOrderVariables
import com.example.m_commerce.features.orders.domain.usecases.CompleteOrderUseCase
import com.example.m_commerce.features.orders.domain.usecases.CreateOrderUseCase
import com.example.m_commerce.features.orders.presentation.ui_state.OrderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class OrderViewModel @Inject constructor(private val createOrderUseCase: CreateOrderUseCase, private val completeOrderUseCase: CompleteOrderUseCase) : ViewModel() {

    private val _state = MutableStateFlow<OrderUiState>(OrderUiState.Idle)
    val state: StateFlow<OrderUiState> = _state


    fun createOrderAndSendEmail(items: List<LineItem>, shippingAddress: ShippingAddress, paymentMethod: PaymentMethod) {
        val variables = DraftOrderCreateVariables(
            email = "youssifn.mostafa@gmail.com", //!GET
            shippingAddress = shippingAddress,
            lineItems = items,
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
                    if(paymentMethod == PaymentMethod.CREDIT_CARD) {
                        //!send email
                    }else{
                        completeOrder(order.id)
                    }
                }
            } catch (e: Exception) {
                _state.value = OrderUiState.Error(e.message ?: "Unknown error")
                Log.e("Shopify", "createOrder:", e )
            }
        }
    }


    private fun completeOrder(draftOrderId: String) {
        val variables = CompleteOrderVariables(id = draftOrderId)
        val request = GraphQLRequest(
            query = completeDraftOrderQuery,
            variables = variables
        )

        viewModelScope.launch {
            _state.value = OrderUiState.Loading
            try {
                completeOrderUseCase(request).collect { completedOrder ->
                    Log.d("Shopify", "Order completed: $completedOrder")
                }
            } catch (e: Exception) {
                _state.value = OrderUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}