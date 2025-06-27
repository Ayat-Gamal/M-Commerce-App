package com.example.m_commerce.features.cart.data.remote

import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.GraphResponse
import com.shopify.buy3.Storefront
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartRemoteDataSourceImplTest {

    private val graphClient = mockk<GraphClient>(relaxed = true)
    private lateinit var dataSource: CartRemoteDataSourceImpl

    @Before
    fun setUp() {
        mockkStatic("com.google.firebase.Firebase")
        mockkStatic("com.google.firebase.firestore.ktx.FirebaseFirestoreKt")
        mockkStatic("com.google.firebase.auth.ktx.AuthKt")
        mockkStatic("com.google.firebase.ktx.Firebase")

        dataSource = CartRemoteDataSourceImpl(graphClient)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `removeProductVariant returns true on successful removal`() = runTest {
        // Arrange
        val cartLineId = "line123"
        val cartId = "cart123"

        //mockFirebaseCartId(cartId)

        val payload = mockk<Storefront.CartLinesRemovePayload> {
            every { userErrors } returns emptyList()
        }

        val response = mockk<GraphResponse<Storefront.Mutation>> {
            every { data?.cartLinesRemove } returns payload
        }

        val result = slot<GraphCallResult<Storefront.Mutation>>()
        every { graphClient.mutateGraph(any()) } answers {
            val callback = arg<((GraphCallResult<Storefront.Mutation>) -> Unit)>(0)
            callback(GraphCallResult.Success(response))
            mockk()
        }

        // Act
        val resultFlow = dataSource.removeProductVariant(cartLineId).first()

        // Assert
        assert(resultFlow)
    }

//
}
