package com.example.m_commerce.features.profile.presentation.viewmodel

import android.util.Log
import com.example.m_commerce.features.profile.domain.usecase.GetCurrenciesUseCase
import com.example.m_commerce.features.profile.domain.usecase.GetDefaultCurrencyUseCase
import com.example.m_commerce.features.profile.domain.usecase.GetExchangeRateUseCase
import com.example.m_commerce.features.profile.domain.usecase.SaveDefaultCurrencyUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
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
class CurrencyViewModelTest {

    private val getCurrenciesUseCase = mockk<GetCurrenciesUseCase>(relaxed = true)
    private val saveDefaultCurrencyUseCase = mockk<SaveDefaultCurrencyUseCase>(relaxed = true)
    private val getDefaultCurrencyUseCase = mockk<GetDefaultCurrencyUseCase>(relaxed = true)
    private val getExchangeRateUseCase = mockk<GetExchangeRateUseCase>(relaxed = true)

    private lateinit var viewModel: CurrencyViewModel

    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Mock Log methods to avoid "not mocked" exception
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0

        val fakeExchangeRateFlow = MutableStateFlow(10f)

        coEvery { getDefaultCurrencyUseCase() } returns "EGP"
        coEvery { getExchangeRateUseCase.exchangeRateFlow } returns fakeExchangeRateFlow
        coEvery { getCurrenciesUseCase() } returns mapOf(
            "EGP" to "Egyptian Pound",
            "USD" to "US Dollar"
        )

        viewModel = CurrencyViewModel(
            getCurrenciesUseCase,
            saveDefaultCurrencyUseCase,
            getDefaultCurrencyUseCase,
            getExchangeRateUseCase
        )
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state loads default currency and symbols`() = runTest {
        advanceUntilIdle()

        assertThat(viewModel.defaultCurrencyCode, `is`("EGP"))

        val currencyMap = viewModel.state.currencies.first().symbols
        assertThat(currencyMap.containsKey("EGP"), `is`(true))
        assertThat(viewModel.state.isLoading, `is`(false))
    }


    @Test
    fun `formatPrice returns correctly formatted string`() = runTest {
        viewModel.exchangeRateState = 10f
        viewModel.defaultCurrencyCode = "EGP"

        val result = viewModel.formatPrice("5")
        assertThat(result, `is`("50.00 EGP"))
    }

    @Test
    fun `formatPrice returns Loading if exchange rate is null`() {
        viewModel.exchangeRateState = null
        val result = viewModel.formatPrice("5")
        assertThat(result, `is`("Loading..."))
    }


}
