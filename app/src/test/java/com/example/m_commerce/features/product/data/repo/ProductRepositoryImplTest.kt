package com.example.m_commerce.features.product.data.repo

import com.example.m_commerce.features.product.data.remote.ProductRemoteDataSource
import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.product.domain.repo.ProductRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductRepositoryImplTest {

    private val remoteDataSource: ProductRemoteDataSource = mockk(relaxed = true)
    private lateinit var repository: ProductRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = ProductRepositoryImpl(remoteDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getProductById returns correct product from remoteDataSource`() = runTest {
        // Given
        val productId = "gid://shopify/Product/123"
        val expectedProduct = Product(
            id = productId,
            title = "Sample Product",
            description = "Some desc",
            price = "20",
            colors = listOf("Red"),
            images = listOf("url"),
            category = "Shoes",
            brand = "Nike",
            variants = emptyList(),
            currencyCode = "",
            sizes = emptyList(),
            availableForSale = true
        )
        coEvery { remoteDataSource.getProductById(productId) } returns flowOf(expectedProduct)

        // When
        val result = repository.getProductById(productId).first()

        // Then
        assertThat(expectedProduct, `is`(result))
    }

    @Test
    fun `addProductVariantToCart returns true when remote succeeds`() = runTest {
        // Given
        val productVariantId = "gid://shopify/ProductVariant/456"
        coEvery { remoteDataSource.addProductVariantToCart(productVariantId,1) } returns flowOf(true)

        // When
        val result = repository.addProductVariantToCart(productVariantId,1).first()

        // Then
        assertTrue(result)
    }

    @Test
    fun `addProductVariantToCart returns false when remote fails`() = runTest {
        // Given
        val productVariantId = "gid://shopify/ProductVariant/456"
        coEvery { remoteDataSource.addProductVariantToCart(productVariantId, 1) } returns flowOf(false)

        // When
        val result = repository.addProductVariantToCart(productVariantId, 1).first()

        // Then
        assertFalse(result)
    }
}
