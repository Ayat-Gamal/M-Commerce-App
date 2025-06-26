//package com.example.m_commerce.features.profile.presentation.viewmodel
//
//import com.example.m_commerce.features.profile.domain.usecase.*
//import io.mockk.*
//import kotlinx.coroutines.*
//import kotlinx.coroutines.flow.flowOf
//import org.bouncycastle.util.test.SimpleTest.runTest
//import org.junit.After
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Test
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class CurrencyViewModelTest {
//
//    private lateinit var viewModel: CurrencyViewModel
//
//    private val getCurrenciesUseCase = mockk<GetCurrenciesUseCase>()
//    private val saveDefaultUseCase = mockk<SaveDefaultCurrencyUseCase>(relaxed = true)
//    private val getDefaultUseCase = mockk<GetDefaultCurrencyUseCase>()
//    private val getExchangeRateUseCase = mockk<GetExchangeRateUseCase>()
//
//    private val testDispatcher = StandardTestDispatcher()
//
//    @Before
//    fun setup() {
//        Dispatchers.setMain(testDispatcher)
//
//        // Return a dummy exchange rate flow
//        every { getExchangeRateUseCase.exchangeRateFlow } returns flowOf(5.0f)
//
//        coEvery { getDefaultUseCase() } returns "EGP"
//        coEvery { getExchangeRateUseCase(any()) } just Runs
//        coEvery { getCurrenciesUseCase() } returns mapOf("EGP" to "Egyptian Pound", "USD" to "US Dollar")
//
//        viewModel = CurrencyViewModel(
//            getCurrenciesUseCase,
//            saveDefaultUseCase,
//            getDefaultUseCase,
//            getExchangeRateUseCase
//        )
//    }
//
//    @Test
//    fun `initial state loads default currency and symbols`() = runTest {
//        advanceUntilIdle()
//
//        assertEquals("EGP", viewModel.defaultCurrencyCode)
//        assertEquals(false, viewModel.state.isLoading)
//        assertTrue(viewModel.state.currencies.isNotEmpty())
//        assertEquals("EGP", viewModel.state.currencies.first().symbols.keys.first())
//    }
//
//    @Test
//    fun `saveDefaultCurrency updates state`() = runTest {
//        coEvery { getExchangeRateUseCase(any()) } just Runs
//
//        viewModel.saveDefaultCurrency("USD")
//        advanceUntilIdle()
//
//        assertEquals("USD", viewModel.defaultCurrencyCode)
//        coVerify { saveDefaultUseCase("USD") }
//    }
//
//    @Test
//    fun `formatPrice returns formatted value`() = runTest {
//        viewModel.exchangeRateState = 10.0f
//        viewModel.defaultCurrencyCode = "EGP"
//
//        val result = viewModel.formatPrice("5")
//        assertEquals("50.00 EGP", result)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//}
