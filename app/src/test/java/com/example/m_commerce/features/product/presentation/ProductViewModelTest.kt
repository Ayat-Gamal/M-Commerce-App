package com.example.m_commerce.features.product.presentation

import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.product.domain.usecases.AddProductVariantToCart
import com.example.m_commerce.features.product.domain.usecases.AddToWishlistUseCase
import com.example.m_commerce.features.product.domain.usecases.CheckIfInWishlistUseCase
import com.example.m_commerce.features.product.domain.usecases.GetProductByIdUseCase
import com.example.m_commerce.features.wishlist.domain.usecases.DeleteFromWishlistUseCase
import com.shopify.buy3.Storefront
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    private val getProductById = mockk<GetProductByIdUseCase>(relaxed = true)
    private val addToWishlist = mockk<AddToWishlistUseCase>(relaxed = true)
    private val deleteFromWishlist = mockk<DeleteFromWishlistUseCase>(relaxed = true)
    private val checkIfInWishlist = mockk<CheckIfInWishlistUseCase>(relaxed = true)
    private val addToCart = mockk<AddProductVariantToCart>(relaxed = true)

    private lateinit var viewModel: ProductViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductViewModel(
            fetchProductById = getProductById,
            addToWishlist = addToWishlist,
            deleteFromWishlist = deleteFromWishlist,
            checkIfInWishlist = checkIfInWishlist,
            addProductVariantToCart = addToCart
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `getProductById emits Success state`() = runTest {

        // given
        val product = mockk<Product> {
            every { id } returns "p1"
        }

        coEvery { getProductById("p1") } returns flowOf(product)
        coEvery { checkIfInWishlist("p1") } returns flowOf(true)

        // when
        viewModel.getProductById("p1")
        advanceUntilIdle()

        val result = ProductUiState.Success(product, true)
        assertThat(viewModel.uiState.value, `is`(result))
    }

    @Test
    fun `getProductById emits Error state on exception`() = runTest {

        // given
        coEvery { getProductById("123") } returns flow { throw Exception("Network error") }

        // when
        viewModel.getProductById("123")
        advanceUntilIdle()

        // then
        assertThat(viewModel.uiState.value is ProductUiState.Error, `is`(true))
    }

    @Test
    fun `addProductToWishlist emits SnackBarMessage`() = runTest {

        // given
        val messages = mutableListOf<SnackBarMessage>()
        coEvery { addToWishlist("p1") } returns flowOf("Added")
        val job = launch { viewModel.message.collect {
            messages.add(it)
        } }

        // when
        viewModel.addProductToWishlist("p1")
        advanceUntilIdle()
        job.cancel()

        // then
        assertThat(messages.isNotEmpty(), `is`(true))
        assertThat(messages.first().message, `is`("Added"))
    }

    @Test
    fun `deleteProductFromWishlist emits SnackBarMessage and calls Undo`() = runTest {

        // given
        val messages = mutableListOf<SnackBarMessage>()
        coEvery { deleteFromWishlist("p1") } returns flowOf("Removed")
        coEvery { addToWishlist("p1") } returns flowOf("Added Back")

        val job = launch {
            viewModel.message.collect { messages.add(it) }
        }

        // when
        viewModel.deleteProductFromWishlist("p1")
        advanceUntilIdle()


        messages.first().onAction?.invoke()
        advanceUntilIdle()

        job.cancel()

        assertThat(messages.first().message, `is`("Removed"))
    }

    @Test
    fun `addToCart emits SnackBarMessage`() = runTest {

        // given
        coEvery { addToCart("p1") } returns flowOf(true)

        val messages = mutableListOf<SnackBarMessage>()
        val job = launch {
            viewModel.message.collect {
                messages.add(it)
            }
        }

        // when
        viewModel.addToCart("p1")
        advanceUntilIdle()
        job.cancel()

        // then
        assertThat(messages.first().message, `is`("Added to cart successfully"))
    }
}
