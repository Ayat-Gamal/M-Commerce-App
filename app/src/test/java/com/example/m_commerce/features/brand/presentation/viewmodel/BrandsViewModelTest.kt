//package com.example.m_commerce.features.brand.presentation.viewmodel
//
//import android.util.Log
//import com.example.m_commerce.features.brand.domain.entity.Brand
//import com.example.m_commerce.features.brand.domain.usecases.GetBrandsUseCase
//import com.example.m_commerce.features.brand.domain.usecases.GetProductsByBrandUseCase
//import com.example.m_commerce.features.brand.presentation.ui_state.BrandsUiState
//import com.example.m_commerce.features.product.domain.entities.Product
//import com.example.m_commerce.features.product.presentation.ui_state.ProductsUiState
//import io.mockk.coEvery
//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.mockkStatic
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.UnconfinedTestDispatcher
//import kotlinx.coroutines.test.advanceUntilIdle
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import org.junit.jupiter.api.Assertions.*
//
//class BrandsViewModelTest{
//
//
//    private val getBrandsUseCase = mockk<GetBrandsUseCase>()
//    private val getProductsUseCase = mockk<GetProductsByBrandUseCase>()
//    private lateinit var viewModel: BrandsViewModel
//
//
//    @Before
//    fun setUp(){
//        Dispatchers.setMain(UnconfinedTestDispatcher())
//
//        mockkStatic("android.util.Log")
//        every { Log.d(any(), any()) } returns 0
//        every { Log.i(any(), any()) } returns 0
//        every { Log.e(any(), any(), any()) } returns 0
//        every { Log.e(any(), any()) } returns 0
//
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//
//
//    @Test
//    fun `init should emit Success when getBrandsUseCase returns data`() = runTest {
//        val mockBrands = listOf(Brand("1", "url", "Nike"))
//        coEvery { getBrandsUseCase(any()) } returns flowOf(mockBrands)
//
//        viewModel = BrandsViewModel(getBrandsUseCase, getProductsUseCase)
//
//        advanceUntilIdle()
//        assert(viewModel.brandsState.value == BrandsUiState.Success(mockBrands))
//    }
//
//    @Test
//    fun `init should emit Error when getBrandsUseCase returns null`() = runTest {
//        coEvery { getBrandsUseCase(any()) } returns flowOf(null)
//
//        viewModel = BrandsViewModel(getBrandsUseCase, getProductsUseCase)
//
//        advanceUntilIdle()
//        assert(viewModel.brandsState.value is BrandsUiState.Error)
//    }
//
//    @Test
//    fun `getProductsByBrandName emits Success when products found`() = runTest {
//        val mockProducts = listOf(
//            Product(
//                id = "1", title = "Shoe", description = "desc", images = listOf(),
//                price = "100", category = "Shoes", currencyCode = "USD",
//                colors = listOf("Black"), sizes = listOf("42"),
//                variants = emptyList(), brand = "Nike"
//            )
//        )
//
//        coEvery { getBrandsUseCase(any()) } returns flowOf(emptyList())
//        coEvery { getProductsUseCase("Nike") } returns flowOf(mockProducts)
//
//        viewModel = BrandsViewModel(getBrandsUseCase, getProductsUseCase)
//        viewModel.getProductsByBrandName("Nike")
//
//        advanceUntilIdle()
//        assert(viewModel.productsState.value == ProductsUiState.Success(mockProducts))
//    }
//
//    @Test
//    fun `getProductsByBrandName emits Error when no products found`() = runTest {
//        coEvery { getBrandsUseCase(any()) } returns flowOf(emptyList())
//        coEvery { getProductsUseCase("Unknown") } returns flowOf(null)
//
//        viewModel = BrandsViewModel(getBrandsUseCase, getProductsUseCase)
//        viewModel.getProductsByBrandName("Unknown")
//
//        advanceUntilIdle()
//        assert(viewModel.productsState.value is ProductsUiState.Error)
//    }
//
//}
//
