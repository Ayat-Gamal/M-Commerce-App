package com.example.m_commerce.features.auth.presentation.login

import com.example.m_commerce.features.auth.domain.usecases.LoginUserUseCase
import com.example.m_commerce.features.auth.domain.validation.ValidateEmail
import com.example.m_commerce.features.auth.domain.validation.ValidatePassword
import com.example.m_commerce.features.auth.domain.validation.ValidationResult
import com.example.m_commerce.features.auth.presentation.register.AuthState
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
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
class LoginViewModelTest {

    private val validateEmail = mockk<ValidateEmail>()
    private val validatePassword = mockk<ValidatePassword>()
    private val loginUser = mockk<LoginUserUseCase>()

    private lateinit var viewModel: LoginViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(validateEmail, validatePassword, loginUser)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `login emits error message when email validation fails`() = runTest {

        // given
        val email = "ahmedsaad"
        val password = "password123"
        val errorMessage = "Invalid email"

        every { validateEmail(email) } returns ValidationResult(false, errorMessage)

        val emittedMessages = mutableListOf<String>()
        val job = launch {
            viewModel.messageState.collect { emittedMessages.add(it) }
        }

        // when
        viewModel.login(email, password)
        advanceUntilIdle()
        job.cancel()

        assertThat(emittedMessages.first(), `is`(errorMessage))

        verify(exactly = 1) { validateEmail(email) }
        verify(exactly = 0) { validatePassword(any()) }
        coVerify(exactly = 0) { loginUser(any(), any()) }
    }

    @Test
    fun `login emits error message when password validation fails`() = runTest {

        // given
        val email = "ahmedsaad@gmail.com"
        val password = "123"
        val errorMessage = "Password too short"

        every { validateEmail(email) } returns ValidationResult(true)
        every { validatePassword(password) } returns ValidationResult(false, errorMessage)

        val emittedMessages = mutableListOf<String>()
        val job = launch {
            viewModel.messageState.collect { emittedMessages.add(it) }
        }


        // when
        viewModel.login(email, password)
        advanceUntilIdle()
        job.cancel()

        // then
        assertThat(emittedMessages.size, `is`(1))
        assertThat(emittedMessages.first(), `is`(errorMessage))

        verify(exactly = 1) { validateEmail(email) }
        verify(exactly = 1) { validatePassword(password) }
        coVerify(exactly = 0) { loginUser(any(), any()) }
    }

    @Test
    fun `login success emits loading and success states`() = runTest {

        // given
        val email = "ahmedsaad@gmail.com"
        val password = "password123"

        every { validateEmail(email) } returns ValidationResult(true)
        every { validatePassword(password) } returns ValidationResult(true)

        val successState = AuthState.Success(mockk(relaxed = true))

        coEvery { loginUser(email, password) } returns flow {
            emit(successState)
        }

        val emittedStates = mutableListOf<AuthState>()
        val job = launch {
            viewModel.uiState.collect {
                emittedStates.add(it)
            }
        }

        // when
        viewModel.login(email, password)
        advanceUntilIdle()
        job.cancel()

       // then
        assertThat(emittedStates.size >= 2, `is`(true))
        assertThat(emittedStates[0], `is`(AuthState.Idle))
        assertThat(emittedStates[1] is AuthState.Success, `is`(true))

        coVerifySequence {
            validateEmail(email)
            validatePassword(password)
            loginUser(email, password)
        }
    }

}
