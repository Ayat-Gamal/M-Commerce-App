package com.example.m_commerce.features.cart.data.repo

import com.example.m_commerce.features.cart.data.remote.CartRemoteDataSource
import com.example.m_commerce.features.cart.domain.entity.Cart
import com.shopify.graphql.support.ID
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
class CartRepositoryImplTest {

    private val cartRemoteDataSource = mockk<CartRemoteDataSource>(relaxed = true)
    private lateinit var repository: CartRepositoryImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = CartRepositoryImpl(cartRemoteDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCartById returns expected cart`() = runTest {
        // Given
        val expectedCart = Cart(
         id =  ID(""),
         createdAt =  "",
         updatedAt =  "",
         lines =  emptyList(),
         totalAmount =  "",
         subtotalAmount =  "",
         totalTaxAmount =  "",
         totalDutyAmount =  "",
         currency =  "EUR",
        )

        coEvery { cartRemoteDataSource.getCartById() } returns flowOf(expectedCart)

        // When
        val result = repository.getCartById()

        // Then
        advanceUntilIdle()
        assertEquals(expectedCart, result.first())
        coVerify { cartRemoteDataSource.getCartById() }
    }

}

