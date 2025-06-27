package com.example.m_commerce.features.categories.data.repository

import android.util.Log
import com.example.m_commerce.features.categories.data.datasources.remote.SubCategoryRemoteDataSource
import com.example.m_commerce.features.categories.data.dto.CategoryDto
import com.example.m_commerce.features.categories.domain.repository.SubCategoryRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


class SubCategoryRepositoryImplTest {


    private val remoteSource = mockk<SubCategoryRemoteDataSource>()
    lateinit var repository: SubCategoryRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        repository = SubCategoryRepositoryImpl(remoteSource)

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
    fun `getCategories returns expected category list`() = runTest {

        val mockCategories = listOf(CategoryDto(id = "omittantur", name = "Janell Baxter", image = "null"))

        every { remoteSource.getCategories() } returns flowOf(mockCategories)

        val result = repository.getCategories().first()

        assert(result == mockCategories)

    }


    @Test
    fun `getCategories returns empty list`() = runTest {
        every { remoteSource.getCategories() } returns flowOf(emptyList())

        val result = repository.getCategories().first()

        assert(result.isEmpty())
    }


}