package com.example.m_commerce.features.orders.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.AddressMangment.domain.entity.Address
import com.example.m_commerce.features.AddressMangment.domain.entity.Response
import com.example.m_commerce.features.AddressMangment.domain.usecases.GetDefaultAddressUseCase
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
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class OrderViewModel @Inject constructor(private val createOrderUseCase: CreateOrderUseCase, private val completeOrderUseCase: CompleteOrderUseCase, private val getDefaultAddressUseCase: GetDefaultAddressUseCase) : ViewModel() {

    private val _state = MutableStateFlow<OrderUiState>(OrderUiState.Idle)
    val state: StateFlow<OrderUiState> = _state


    fun createOrderAndSendEmail(items: List<LineItem>, paymentMethod: PaymentMethod) {
        viewModelScope.launch {
            _state.value = OrderUiState.Loading

            Log.d("RETURN DEFAULT ADDRESS", "TEST 1")

            try {
                val user = FirebaseAuth.getInstance().currentUser ?: run {
                    _state.value = OrderUiState.Error("User not authenticated")
            Log.d("RETURN DEFAULT ADDRESS", "TEST 2")
                    return@launch
                }

            Log.d("RETURN DEFAULT ADDRESS", "TEST 2")
                var defaultAddress: Address? = null
                getDefaultAddressUseCase().collect { response ->
                    when (response) {
                        is Response.Success -> {
                            defaultAddress = response.data
                            return@collect
                        }
                        is Response.Error -> {
                            _state.value = OrderUiState.Error(response.message)
                            return@collect
                        }
                        Response.Loading -> { }
                    }
                }

                if (defaultAddress == null) {
                    _state.value = OrderUiState.Error("No address received")
                    return@launch
                }
                Log.d("RETURN DEFAULT ADDRESS", "createOrderAndSendEmail: ${defaultAddress}")

                val shippingAddress = ShippingAddress(
                    address1 = defaultAddress!!.address1 ,
                    city = defaultAddress!!.city,
                    country = defaultAddress!!.country,
                    firstName = user.displayName ?: "Guest",
                    lastName = "",
                    zip = defaultAddress!!.zip
                )

                val variables = DraftOrderCreateVariables(
//                    email = user.email ?: "",
                    email = "youssifn.mostafa@gmail.com" ?: "",
                    shippingAddress = shippingAddress,
                    lineItems = items,
                    note = null
                )

                val request = GraphQLRequest(
                    query = createDraftOrderQuery,
                    variables = variables
                )

                createOrderUseCase(request).collect { order ->
                    Log.d("Shopify", "Order created: ${order.id}")

                    when (paymentMethod) {
                        PaymentMethod.CREDIT_CARD -> {
                            //sendOrderConfirmationEmail(order.id)
                            _state.value = OrderUiState.Success(order)
                        }
                        else -> {
                            completeOrder(order.id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("Shopify", "Order creation failed", e)
                _state.value = OrderUiState.Error(e.message ?: "Failed to create order")
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