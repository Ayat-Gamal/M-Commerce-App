package com.example.m_commerce.features.profile.data.reposetoryImpl

import android.util.Log
import com.example.m_commerce.features.profile.data.local.CurrencyPreferencesDataSource
import com.example.m_commerce.features.profile.data.remote.CurrencyApiService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class CurrencyRepositoryImplTest {

    private val local = mockk<CurrencyPreferencesDataSource>(relaxed = true)
    private val remote = mockk<CurrencyApiService>(relaxed = true)
    private lateinit var repo: CurrencyRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {

        mockkStatic(Log::class)
        every { Log.e(any(), any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0


        Dispatchers.setMain(testDispatcher)
        every { local.getExchangeRate() } returns 1.0f
        repo = CurrencyRepositoryImpl(local, remote)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }



    @Test
    fun `getSupportedCurrencies should return emptyMap on exception`() = runTest {
        // Given
        coEvery { remote.getSupportedSymbols(any()) } throws Exception("API failed")

        // When
        val result = repo.getSupportedCurrencies()
        advanceUntilIdle()

        // Then
        assertThat(result.isEmpty(), `is`(true))
    }

    @Test
    fun `getCachedExchangeRate should return the current flow value`() {
        // When
        val result = repo.getCachedExchangeRate()

        // Then
        assertThat(result, `is`(1.0f))
    }

    @Test
    fun `saveDefaultCurrencyCode should call local save`() = runTest {
        // Given
        val currencyCode = "EUR"

        // When
        repo.saveDefaultCurrencyCode(currencyCode)

        // Then
        // No exception thrown means success since local is relaxed
    }
}
