package com.example.m_commerce.features.AddressMangment.presentation.viewmodel
import com.example.m_commerce.core.utils.NetworkManager
import com.example.m_commerce.features.AddressMangment.domain.entity.Address
import com.example.m_commerce.features.AddressMangment.domain.entity.DeleteResponse
import com.example.m_commerce.features.AddressMangment.domain.entity.Response
import com.example.m_commerce.features.AddressMangment.domain.usecases.DeleteAddressUseCase
import com.example.m_commerce.features.AddressMangment.domain.usecases.GetAddressesUseCase
import com.example.m_commerce.features.AddressMangment.domain.usecases.GetDefaultAddressUseCase
import com.example.m_commerce.features.AddressMangment.domain.usecases.SaveAddressUseCase
import com.example.m_commerce.features.AddressMangment.domain.usecases.SetDefaultAddressUseCase
import com.example.m_commerce.features.AddressMangment.presentation.ui_states.DeleteState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class AddressViewModelTest {

    private val saveAddressUseCase = mockk<SaveAddressUseCase>(relaxed = true)
    private val getAddressesUseCase = mockk<GetAddressesUseCase>(relaxed = true)
    private val getDefaultAddressUseCase = mockk<GetDefaultAddressUseCase>(relaxed = true)
    private val setDefaultAddressUseCase = mockk<SetDefaultAddressUseCase>(relaxed = true)
    private val deleteAddressUseCase = mockk<DeleteAddressUseCase>(relaxed = true)
    private val networkManager = mockk<NetworkManager>(relaxed = true)
    private val firebaseAuth = mockk<FirebaseAuth>(relaxed = true)

    private lateinit var viewModel: AddressViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        val mockUser = mockk<FirebaseUser>()
        every { firebaseAuth.currentUser } returns mockUser
        every { mockUser.uid } returns "user123"
        every { mockUser.displayName } returns "John"


        viewModel = AddressViewModel(
            saveAddressUseCase,
            getAddressesUseCase,
            getDefaultAddressUseCase,
            setDefaultAddressUseCase,
            deleteAddressUseCase,
            firebaseAuth,
            networkManager
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `loadAddresses loads data and sets default address`() = runTest {
        val mockAddress = Address("user123", "John", "", "Street 1", "City", "Country", "12345", "Home", "1234567890")
        coEvery { getAddressesUseCase() } returns flowOf(Response.Success(listOf(mockAddress)))
        coEvery { getDefaultAddressUseCase() } returns flowOf(Response.Success(mockAddress))

        viewModel.loadAddresses()
        advanceUntilIdle()

        assertThat(viewModel.addresses.value.first().id, `is`("user123"))
        assertThat(viewModel.defaultAddress.value?.id, `is`("user123"))
    }

    @Test
    fun `deleteAddress updates state on success`() = runTest {
        val addressId = "address123"
        val response = DeleteResponse( addressId)
        coEvery { deleteAddressUseCase(addressId) } returns flowOf(Response.Success(response))
        coEvery { getAddressesUseCase() } returns flowOf(Response.Success(emptyList()))
        coEvery { getDefaultAddressUseCase() } returns flowOf(Response.Success(Address("1", "Mohamed", "Taj", "Alex", "Home", "Alex", "2050", "Egypt", "0123456789")))

        viewModel.deleteAddress(addressId)
        advanceUntilIdle()

        assertThat(viewModel.deleteState.value, `is`(DeleteState.Success(addressId)))
    }
}
