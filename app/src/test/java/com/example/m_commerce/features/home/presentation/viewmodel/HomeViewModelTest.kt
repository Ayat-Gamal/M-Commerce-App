//package com.example.m_commerce.features.home.presentation.viewmodel
//
//import org.junit.jupiter.api.Assertions.*
//
//
//import android.util.Log
//import com.example.m_commerce.features.brand.domain.entity.Brand
//import com.example.m_commerce.features.brand.domain.usecases.GetBrandsUseCase
//import com.example.m_commerce.features.brand.domain.usecases.GetProductsByBrandUseCase
//import com.example.m_commerce.features.brand.presentation.ui_state.BrandsUiState
//import com.example.m_commerce.features.coupon.domain.entity.Coupon
//import com.example.m_commerce.features.coupon.domain.usecases.GetCouponsUseCase
//import com.example.m_commerce.features.home.presentation.ui_state.HomeUiState
//import com.example.m_commerce.features.product.domain.entities.Product
//import com.example.m_commerce.features.product.presentation.ui_state.ProductsUiState
//import io.mockk.coEvery
//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.mockkStatic
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.UnconfinedTestDispatcher
//import kotlinx.coroutines.test.advanceUntilIdle
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class HomeViewModelTest {
//
//    private val getBrandsUseCase = mockk<GetBrandsUseCase>()
//    private val getCouponsUseCase = mockk<GetCouponsUseCase>()
//    private lateinit var viewModel: HomeViewModel
//
//    @Before
//    fun setUp() {
//        Dispatchers.setMain(UnconfinedTestDispatcher())
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
//    @Test
//    fun `getHomeData emits Success when brands and coupons are present`() = runTest {
//        val mockCoupons = listOf(Coupon(1, "SALE20", 2, "enabled", "20% off"))
//        val mockBrands = listOf(Brand("1", "url", "Nike"))
//
//        coEvery { getCouponsUseCase() } returns flowOf(mockCoupons)
//        coEvery { getBrandsUseCase(any()) } returns flowOf(mockBrands)
//
//        viewModel = HomeViewModel(getBrandsUseCase, getCouponsUseCase)
//        viewModel.getHomeData()
//
//        advanceUntilIdle()
//        assert(viewModel.dataState.value == HomeUiState.Success(mockBrands, mockCoupons))
//    }
//
//    @Test
//    fun `getHomeData emits Error when brands are null`() = runTest {
//        coEvery { getCouponsUseCase() } returns flowOf(emptyList())
//        coEvery { getBrandsUseCase(any()) } returns flowOf(null)
//
//        viewModel = HomeViewModel(getBrandsUseCase, getCouponsUseCase)
//        viewModel.getHomeData()
//
//        advanceUntilIdle()
//        assert(viewModel.dataState.value is HomeUiState.Error)
//    }
//
//    @Test
//    fun `getHomeData emits Error when exception thrown`() = runTest {
//        coEvery { getCouponsUseCase() } throws RuntimeException("Failed to get coupons")
//        coEvery { getBrandsUseCase(any()) } returns flowOf(emptyList())
//
//        viewModel = HomeViewModel(getBrandsUseCase, getCouponsUseCase)
//        viewModel.getHomeData()
//
//        advanceUntilIdle()
//        val state = viewModel.dataState.value
//        assert(state is HomeUiState.Error && state.message.contains("Failed"))
//    }
//
//}
