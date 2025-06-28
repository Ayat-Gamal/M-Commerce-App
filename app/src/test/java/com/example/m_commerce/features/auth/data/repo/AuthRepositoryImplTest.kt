package com.example.m_commerce.features.auth.data.repo

import com.example.m_commerce.features.auth.data.remote.CustomersRemoteDataSource
import com.example.m_commerce.features.auth.data.remote.UsersRemoteDataSource
import com.example.m_commerce.features.auth.presentation.register.AuthState
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
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
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryImplTest {

    private val userDataSource: UsersRemoteDataSource = mockk()
    private val shopifyDataSource: CustomersRemoteDataSource = mockk()
    private lateinit var repository: AuthRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = AuthRepositoryImpl(userDataSource, shopifyDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `registerUser should delegate to userDataSource`() = runTest {
        val email = "test@example.com"
        val password = "123456"
        val expectedState = AuthState.Success(mockk(relaxed = true))
        coEvery { userDataSource.registerWithEmail(email, password) } returns flowOf(expectedState)

        val result = repository.registerUser(email, password).first()

        assertThat(result, `is`(expectedState))
        coVerify(exactly = 1) { userDataSource.registerWithEmail(email, password) }
    }

    @Test
    fun `loginUser should delegate to userDataSource`() = runTest {
        val email = "test@example.com"
        val password = "123456"
        val expectedState = AuthState.Success(mockk(relaxed = true))
        coEvery { userDataSource.loginUser(email, password) } returns flowOf(expectedState)

        val result = repository.loginUser(email, password).first()

        assertThat(result, `is`(expectedState))
        coVerify { userDataSource.loginUser(email, password) }
    }

    @Test
    fun `sendEmailVerification should delegate to userDataSource`() = runTest {
        val mockUser = mockk<FirebaseUser>()
        val expectedState = AuthState.Success(mockk(relaxed = true))
        coEvery { userDataSource.sendEmailVerification(mockUser) } returns flowOf(expectedState)

        val result = repository.sendEmailVerification(mockUser).first()

        assertThat(result, `is`(expectedState))
        coVerify { userDataSource.sendEmailVerification(mockUser) }
    }

    @Test
    fun `createCustomerToken should delegate to shopifyDataSource`() = runTest {
        val email = "test@example.com"
        val password = "123456"
        val name = "Test User"
        val token = "shopify_token"
        coEvery { shopifyDataSource.createCustomerToken(email, password, name) } returns flowOf(token)

        val result = repository.createCustomerToken(email, password, name).first()

        assertThat(result, `is`(token))
        coVerify { shopifyDataSource.createCustomerToken(email, password, name) }
    }

    @Test
    fun `createCart should delegate to shopifyDataSource`() = runTest {
        val cartId = "cart123"
        coEvery { shopifyDataSource.createCustomerCart() } returns flowOf(cartId)

        val result = repository.createCart("token123").first()

        assertThat(result, `is`(cartId))
        coVerify { shopifyDataSource.createCustomerCart() }
    }

    @Test
    fun `storeTokenAndCartId should delegate to userDataSource`() = runTest {
        val token = "tok"
        val cartId = "cartId"
        val uid = "uid123"
        coEvery { userDataSource.storeTokenAndCartId(token, cartId, uid) } returns Unit

        repository.storeTokenAndCartId(token, cartId, uid)

        coVerify { userDataSource.storeTokenAndCartId(token, cartId, uid) }
    }
}
