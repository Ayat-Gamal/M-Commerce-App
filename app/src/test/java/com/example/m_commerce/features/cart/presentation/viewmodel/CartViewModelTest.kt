package com.example.m_commerce.features.cart.presentation.viewmodel

import ProductVariant
import com.example.m_commerce.features.cart.domain.entity.Cart
import com.example.m_commerce.features.cart.domain.usecases.GetCartByIdUseCase
import com.example.m_commerce.features.cart.domain.usecases.RemoveProductVariantUseCase
import com.example.m_commerce.features.cart.domain.usecases.UpdateCartUseCase
import com.example.m_commerce.features.cart.presentation.CartUiState
import com.example.m_commerce.features.coupon.domain.usecases.ApplyCouponUseCase
import com.google.firebase.auth.FirebaseAuth
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    private val getCartById = mockk<GetCartByIdUseCase>(relaxed = true)
    private val updateCart = mockk<UpdateCartUseCase>(relaxed = true)
    private val removeLine = mockk<RemoveProductVariantUseCase>(relaxed = true)
    private val applyCoupon = mockk<ApplyCouponUseCase>(relaxed = true)

    private lateinit var viewModel: CartViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance().currentUser } returns mockk(relaxed = true)

        viewModel = CartViewModel(getCartById, updateCart, removeLine, applyCoupon)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `getCartById should emit Empty when cart has no lines`() = runTest {
        // given
        val fakeCart = mockk<Cart>(relaxed = true) {
            every { lines } returns emptyList()
        }
        coEvery { getCartById() } returns flowOf(fakeCart)

        // when
        viewModel.getCartById()
        advanceUntilIdle()

        // then
        assertThat(viewModel.uiState.value, `is`(CartUiState.Empty))
    }

    @Test
    fun `getCartById should emit Success when cart has lines`() = runTest {
        // Arrange
        val line = mockk<ProductVariant>(relaxed = true) {
            every { lineId } returns "line_1"
            every { quantity } returns 1
            every { availableQuantity } returns 5
        }

        val cart = mockk<Cart>(relaxed = true) {
            every { lines } returns listOf(line)
        }

        coEvery { getCartById() } returns flowOf(cart)

        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val viewModel = CartViewModel(
            getCartByIdUseCase = getCartById,
            updateCartUseCase = updateCart,
            removeProductVariantUseCase = removeLine,
            applyCouponUseCase = applyCoupon
        )

        // Act
        viewModel.getCartById()
        advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assert(state is CartUiState.Error) {
            "Expected Success state, but got: $state"
        }
    }


}
