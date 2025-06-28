package com.example.m_commerce.features.orders.data.repository

import android.util.Log
import com.example.m_commerce.features.orders.data.model.GraphQLRequest
import com.example.m_commerce.features.orders.data.model.variables.CompleteOrderVariables
import com.example.m_commerce.features.orders.data.model.variables.DraftOrderCreateVariables
import com.example.m_commerce.features.orders.data.model.variables.ShippingAddress
import com.example.m_commerce.features.orders.data.remote.OrderDataSource
import com.example.m_commerce.features.orders.domain.entity.CompletedOrder
import com.example.m_commerce.features.orders.domain.entity.CreatedOrder
import com.example.m_commerce.features.orders.presentation.ui_state.OrderHistoryUiState
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrderRepositoryImplTest {

    private lateinit var repository: OrderRepositoryImpl
    private val orderDataSource = mockk<OrderDataSource>()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = OrderRepositoryImpl(orderDataSource)

        mockkStatic("android.util.Log")
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `createOrder returns expected result`() = runTest {
        val mockCreatedOrder = CreatedOrder(
            id = "123",
            number = "#123",
            createdAt = "2024-06-25T12:34:56Z",
            invoiceUrl = "https://example.com/invoice/123"
        )

        val request = GraphQLRequest(
            query = "mutation",
            variables = DraftOrderCreateVariables(
                email = "test@example.com",
                shippingAddress = ShippingAddress(
                    firstName = "Youssif",
                    lastName = "Nasser",
                    address1 = "123 St",
                    city = "Alex",
                    country = "Egypt",
                    zip = "12345"
                ),
                lineItems = listOf()
            )
        )

        every { orderDataSource.createOrder(request, "token") } returns flowOf(mockCreatedOrder)

        val result = repository.createOrder(request, "token").first()

        assert(result == mockCreatedOrder)
        verify { orderDataSource.createOrder(request, "token") }
    }

    @Test
    fun `completeOrder returns expected result`() = runTest {
        val mockCompletedOrder = CompletedOrder(
            id = "456",
            name = "Order #456",
            status = "COMPLETED"
        )

        val request = GraphQLRequest(
            query = "mutation",
            variables = CompleteOrderVariables(id = "456")
        )

        every { orderDataSource.completeOrder(request, "token") } returns flowOf(mockCompletedOrder)

        val result = repository.completeOrder(request, "token").first()

        assert(result == mockCompletedOrder)
        verify { orderDataSource.completeOrder(request, "token") }
    }


    @Test
    fun `getOrders returns expected OrderHistoryUiState`() = runTest {
        val mockState = OrderHistoryUiState.Success(emptyList())

        every { orderDataSource.fetchOrders() } returns flowOf(mockState)

        val result = repository.getOrders().first()

        assert(result == mockState)
        verify { orderDataSource.fetchOrders() }
    }
}