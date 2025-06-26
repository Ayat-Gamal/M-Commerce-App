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
import com.example.m_commerce.features.orders.data.model.GraphQLRequest
import com.example.m_commerce.features.orders.data.model.completeDraftOrderQuery
import com.example.m_commerce.features.orders.data.model.createDraftOrderQuery
import com.example.m_commerce.features.orders.data.model.variables.CompleteOrderVariables
import com.example.m_commerce.features.orders.data.model.variables.DraftOrderCreateVariables
import com.example.m_commerce.features.orders.data.model.variables.LineItem
import com.example.m_commerce.features.orders.data.model.variables.ShippingAddress
import com.example.m_commerce.features.orders.domain.usecases.CompleteOrderUseCase
import com.example.m_commerce.features.orders.domain.usecases.CreateOrderUseCase
import com.example.m_commerce.features.orders.domain.usecases.GetOrdersUseCase
import com.example.m_commerce.features.orders.presentation.ui_state.OrderHistoryUiState
import com.example.m_commerce.features.orders.presentation.ui_state.OrderUiState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val createOrderUseCase: CreateOrderUseCase,
    private val completeOrderUseCase: CompleteOrderUseCase,
    private val getDefaultAddressUseCase: GetDefaultAddressUseCase,
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<OrderUiState>(OrderUiState.Idle)
    val state: StateFlow<OrderUiState> = _state


    private val _ordersState = MutableStateFlow<OrderHistoryUiState>(OrderHistoryUiState.Loading)
    val ordersState: StateFlow<OrderHistoryUiState> = _ordersState


    fun loadOrders() =
        viewModelScope.launch {
            Log.d("OrderHistory", "ViewModel loading orders")


            getOrdersUseCase()
                .catch { e -> _ordersState.value = OrderHistoryUiState.Error(e.message ?: "Unknown error") }
                .collect { result ->
                    Log.i("OrderHistory", "loadOrders: ${_ordersState.value}")
                    _ordersState.value = result
                }

        }

//    fun createOrderAndSendEmail(items: List<LineItem>, paymentMethod: PaymentMethod) {
//        viewModelScope.launch {
//            _state.value = OrderUiState.Loading
//
//            try {
//                val user = FirebaseAuth.getInstance().currentUser
//                val addressResponse = getDefaultAddressUseCase().first()
//
//                val defaultAddress = when (addressResponse) {
//                    is Response.Success -> {
//                        Log.i("Order", "address SUCCESS: ${addressResponse.data}")
//                        addressResponse.data
//                    }
//
//                    is Response.Error -> {
//                        _state.value = OrderUiState.Error(addressResponse.message)
//                        Log.i("Order", "Test 1: ${addressResponse.message}")
//                        return@launch
//                    }
//
//                    is Response.Loading -> Address(
//                        address1 = "asdsdasdasd",
//                        address2 = "wqweqwe",
//                        city = "ssss",
//                        country = "cairo",
//                        firstName = "joooo",
//                        lastName = "",
//                        phone = "222222",
//                        zip = "123"
//                    )
//
//                }
//
//                Log.i("Order", "address: ${defaultAddress}")
//                if (defaultAddress == null) {
//                    _state.value = OrderUiState.Error("No default address found")
//                    Log.i("Order", "No default address found")
//                    //return@launch
//                }
//
//                val shippingAddress = ShippingAddress(
//                    address1 = defaultAddress.address1,
//                    city = defaultAddress.city ?: "cairo",
//                    country = defaultAddress.country ?: "",
//                    firstName = defaultAddress.firstName, // user??.displayName ?: "Guest"
//                    lastName = "",
//                    zip = defaultAddress.zip ?: ""
//                )
//                Log.i("Order", "address: ${shippingAddress}")
//
//                val request = GraphQLRequest(
//                    query = createDraftOrderQuery,
//                    variables = DraftOrderCreateVariables(
//                        email = "youssifn.mostafa@gmail.com" /*?: user?.email*/,
//                        shippingAddress = shippingAddress,
//                        lineItems = items,
//                        note = ""
//                    )
//                )
//
//                createOrderUseCase(request)
//                    .catch { e -> _state.value = OrderUiState.Error(e.message ?: "Order creation failed")
//
//                        Log.e("Order", "Order Error:", e)
//                    }
//                    .collect { order ->
//                        Log.d("Order", "Order created: ${order.id}")
//
//                        if (paymentMethod == PaymentMethod.CreditCard) {
//                            _state.value = OrderUiState.Success(order)
//                        } else {
//                            completeOrder(order.id)
//                        }
//                    }
//
//            } catch (e: Exception) {
//                Log.e("Order", "Order creation failed", e)
//                _state.value = OrderUiState.Error(e.message ?: "Failed to create order")
//            }
//        }
//    }

    fun createOrderAndSendEmail(items: List<LineItem>, paymentMethod: PaymentMethod) {
        viewModelScope.launch {
            _state.value = OrderUiState.Loading

            Log.d("Order", "TEST 1")

            try {
                val user = FirebaseAuth.getInstance().currentUser ?: run {
                    _state.value = OrderUiState.Error("User not authenticated")
                    Log.d("RETURN DEFAULT ADDRESS", "TEST 2")
                    return@launch
                }

                Log.d("Order", "TEST 3")
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

                        Response.Loading -> {}
                    }
                }

                if (defaultAddress == null) {
                    _state.value = OrderUiState.Error("No address received")
                    Log.d("Order", "TEST 4")
                    return@launch
                }
//                Log.d("RETURN DEFAULT ADDRESS", "createOrderAndSendEmail: ${defaultAddress}")

                val shippingAddress = ShippingAddress(
                    address1 = defaultAddress!!.address1,
                    city = defaultAddress!!.city,
                    country = defaultAddress!!.country,
                    firstName = user.displayName ?: "Guest",
                    lastName = "",
                    zip = defaultAddress!!.zip
                )

                val variables = DraftOrderCreateVariables(
                    email = user.email ?: "youssifn.mostafa@gmail.com",
//                    email = "youssifn.mostafa@gmail.com",
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
                        PaymentMethod.CreditCard -> {
                            //sendOrderConfirmationEmail(order.id)
                            _state.value = OrderUiState.Success(order)
                        }

                        else -> {
                            Log.i("Order", "createOrderAndSendEmail: ${order.id}")
//                            completeOrder("gid://shopify/Order/6259465453817")
                            completeOrder(order.id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("Order", "Order creation failed", e)
                _state.value = OrderUiState.Error(e.message ?: "Failed to create order")
            }
        }
    }

    fun completeOrder(draftOrderId: String) {
        Log.i("Order", "completeOrder: $draftOrderId")
        val variables = CompleteOrderVariables(id = draftOrderId)
        val request = GraphQLRequest(
            query = completeDraftOrderQuery,
            variables = variables
        )

        viewModelScope.launch {
            _state.value = OrderUiState.Loading
            try {
                completeOrderUseCase(request).collect { completedOrder ->
                    Log.d("Order", "Order completed: $completedOrder")
                }
            } catch (e: Exception) {
                _state.value = OrderUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}