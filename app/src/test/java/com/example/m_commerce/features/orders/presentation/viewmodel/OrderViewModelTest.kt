package com.example.m_commerce.features.orders.presentation.viewmodel

import android.util.Log
import com.example.m_commerce.features.AddressMangment.domain.entity.Address
import com.example.m_commerce.features.AddressMangment.domain.entity.Response
import com.example.m_commerce.features.AddressMangment.domain.usecases.GetDefaultAddressUseCase
import com.example.m_commerce.features.orders.data.PaymentMethod
import com.example.m_commerce.features.orders.data.model.variables.LineItem
import com.example.m_commerce.features.orders.domain.entity.OrderHistory
import com.example.m_commerce.features.orders.domain.usecases.CompleteOrderUseCase
import com.example.m_commerce.features.orders.domain.usecases.CreateOrderUseCase
import com.example.m_commerce.features.orders.domain.usecases.GetOrdersUseCase
import com.example.m_commerce.features.orders.presentation.ui_state.OrderHistoryUiState
import com.example.m_commerce.features.orders.presentation.ui_state.OrderUiState
import com.google.firebase.auth.FirebaseAuth
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class OrderViewModelTest {

    private val createOrderUseCase = mockk<CreateOrderUseCase>()
    private val completeOrderUseCase = mockk<CompleteOrderUseCase>()
    private val getDefaultAddressUseCase = mockk<GetDefaultAddressUseCase>()
    private val getOrdersUseCase = mockk<GetOrdersUseCase>()

    private lateinit var viewModel: OrderViewModel

    @Before
    fun setUp() {
        mockkStatic(FirebaseAuth::class)
        mockkStatic("android.util.Log")
        every { FirebaseAuth.getInstance().currentUser } returns mockk {
            every { displayName } returns "Test User"
            every { email } returns "test@example.com"
        }

        Dispatchers.setMain(UnconfinedTestDispatcher())


        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0

        viewModel = OrderViewModel(
            createOrderUseCase,
            completeOrderUseCase,
            getDefaultAddressUseCase,
            getOrdersUseCase
        )
    }

    @After
    fun cleanupDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadOrders emits Success state`() = runTest {
        val expectedState = OrderHistoryUiState.Success(emptyList())

        coEvery { getOrdersUseCase() } returns flow {
            emit(expectedState)
        }

        viewModel.loadOrders()
        advanceUntilIdle()

        assertEquals(expectedState, viewModel.ordersState.value)
    }




//
//    @Test
//    fun `createOrderAndSendEmail emits Success when address is valid and payment is CreditCard`() = runTest {
//        val address = Address(
//            address1 = "123 St",
//            address2 = "Apt 4",
//            city = "Cairo",
//            country = "Egypt",
//            firstName = "Ahmed",
//            lastName = "Saad",
//            phone = "123456",
//            zip = "11111"
//        )
//
//        val lineItems = listOf(
//            LineItem(variantId = "variant-1", quantity = 2)
//        )
//
//        val draftOrder = mockk<Order> {
//            every { id } returns "gid://shopify/Order/123456"
//        }
//
//        coEvery { getDefaultAddressUseCase() } returns flowOf(Response.Success(address))
//        coEvery { createOrderUseCase(any()) } returns flowOf(draftOrder)
//
//        viewModel.createOrderAndSendEmail(lineItems, PaymentMethod.CreditCard)
//        advanceUntilIdle()
//
//        assertEquals(OrderUiState.Success(draftOrder), viewModel.state.value)
//    }
//
//    @Test
//    fun `createOrderAndSendEmail calls completeOrder if payment method is Cash`() = runTest {
//        val address = Address(
//            address1 = "123 St",
//            address2 = "Apt 4",
//            city = "Cairo",
//            country = "Egypt",
//            firstName = "Ahmed",
//            lastName = "Saad",
//            phone = "123456",
//            zip = "11111"
//        )
//
//        val order = mockk<Order> {
//            every { id } returns "gid://shopify/Order/999999"
//        }
//
//        coEvery { getDefaultAddressUseCase() } returns flowOf(Response.Success(address))
//        coEvery { createOrderUseCase(any()) } returns flowOf(order)
//        coEvery { completeOrderUseCase(any()) } returns flowOf(order)
//
//        viewModel.createOrderAndSendEmail(listOf(), PaymentMethod.CashOnDelivery)
//        advanceUntilIdle()
//
//        // Optional: assert the state is still Success, or just ensure no crash
//        assert(viewModel.state.value !is OrderUiState.Error)
//    }
//
//    @Test
//    fun `completeOrder emits Error on failure`() = runTest {
//        coEvery { completeOrderUseCase(any()) } throws RuntimeException("Server down")
//
//        viewModel.completeOrder("gid://shopify/Order/123456")
//        advanceUntilIdle()
//
//        assert(viewModel.state.value is OrderUiState.Error)
//    }
}