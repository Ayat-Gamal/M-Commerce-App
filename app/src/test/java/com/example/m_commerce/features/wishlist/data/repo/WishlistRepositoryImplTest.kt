package com.example.m_commerce.features.wishlist.data.repo

import com.example.m_commerce.features.wishlist.data.remote.WishlistRemoteDataSource
import com.example.m_commerce.features.wishlist.domain.repo.WishlistRepository
import io.mockk.coEvery
import io.mockk.coVerify
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
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.AfterEach

@OptIn(ExperimentalCoroutinesApi::class)
class WishlistRepositoryImplTest {

    private val remoteDataSource = mockk<WishlistRemoteDataSource>(relaxed = true)
    private lateinit var repository: WishlistRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = WishlistRepositoryImpl(remoteDataSource)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addToWishlist should return success message`() = runTest {
        // Given
        val productVariantId = "variant123"
        val expectedMessage = "Added to wishlist"
        coEvery { remoteDataSource.addToWishlist(productVariantId) } returns flowOf(expectedMessage)

        // When
        val result = repository.addToWishlist(productVariantId).first()

        // Then
        assertThat(result, `is`(expectedMessage))
        coVerify { remoteDataSource.addToWishlist(productVariantId) }
    }

    @Test
    fun `deleteFromWishlist should complete without error`() = runTest {
        val productVariantId = "variant123"
        val expectedMessage = "Removed from wishlist"
        coEvery { remoteDataSource.deleteFromWishlist(productVariantId) } returns flowOf(
            expectedMessage
        )

        val result = repository.deleteFromWishlist(productVariantId).first()

        assertThat(result, `is`(expectedMessage))
        coVerify { remoteDataSource.deleteFromWishlist(productVariantId) }
    }

    @Test
    fun `isInWishlist should return true`() = runTest {
        val productVariantId = "variant123"
        coEvery { remoteDataSource.isInWishlist(productVariantId) } returns flowOf(true)

        val result = repository.isInWishlist(productVariantId).first()

        assertThat(result, `is`(true))
        coVerify { remoteDataSource.isInWishlist(productVariantId) }
    }

    @Test
    fun `getWishlist should return list of ids`() = runTest {
        val expectedIds = listOf("id1", "id2")
        coEvery { remoteDataSource.getWishlist() } returns flowOf(expectedIds)

        val result = repository.getWishlist().first()

        assertThat(result, `is`(expectedIds))
        coVerify { remoteDataSource.getWishlist() }
    }
}
