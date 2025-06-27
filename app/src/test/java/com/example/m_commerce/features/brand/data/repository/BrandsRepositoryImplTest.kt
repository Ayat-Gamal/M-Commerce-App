package com.example.m_commerce.features.brand.data.repository

import android.util.Log
import com.example.m_commerce.features.brand.data.datasources.remote.BrandsRemoteDataSource
import com.example.m_commerce.features.brand.data.dto.BrandDto
import com.example.m_commerce.features.product.domain.entities.Product
import io.mockk.coEvery
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
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class BrandsRepositoryImplTest {

    private lateinit var repository: BrandsRepositoryImpl
    private val remoteSource = mockk<BrandsRemoteDataSource>()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = BrandsRepositoryImpl(remoteSource)

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
    fun `getBrands returns expected brand list`() = runTest {
        val mockBrands = listOf(BrandDto("1", "image_url", "Nike"))
        every { remoteSource.getBrands(30) } returns flowOf(mockBrands)

        val result = repository.getBrands(30).first()

        assert(result == mockBrands)
        verify(exactly = 1) { remoteSource.getBrands(30) }
    }

    @Test
    fun `getProductsByBrandName returns expected product list`() = runTest {
        val mockProducts = listOf(
            Product(
                id = "123", title = "Shoe", description = "desc", images = listOf(),
                price = "999", category = "Footwear", currencyCode = "EGP",
                colors = listOf(), sizes = listOf(), variants = listOf(), brand = "Nike"
            )
        )

        coEvery { remoteSource.getProductsByBrandName("Nike") } returns flowOf(mockProducts)

        val result = repository.getProductsByBrandName("Nike").first()

        assert(result == mockProducts)
        verify { remoteSource.getProductsByBrandName("Nike") }
    }
}