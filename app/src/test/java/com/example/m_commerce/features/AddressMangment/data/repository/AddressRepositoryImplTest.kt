package com.example.m_commerce.features.AddressMangment.data.repository


import com.example.m_commerce.features.AddressMangment.data.remote.AddressDataSource
import com.example.m_commerce.features.AddressMangment.domain.entity.Address
import com.example.m_commerce.features.AddressMangment.domain.entity.DeleteResponse
import com.example.m_commerce.features.AddressMangment.domain.entity.Response
import com.example.m_commerce.features.AddressMangment.domain.repository.AddressRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class AddressRepositoryImplTest {

    private lateinit var repository: AddressRepository
    private val remoteDataSource = mockk<AddressDataSource>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    private val fakeAddress = Address(
        id = "1",
        firstName = "John",
        lastName = "Doe",
        address1 = "123 Street",
        city = "Cairo",
        country = "Egypt",
        zip = "12345",
        address2 = "Home",
        phone = "0100000000"
    )

    @Before
    fun setup() {
        repository = AddressRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `saveAddress should return Success`() = runTest(testDispatcher) {
        coEvery { remoteDataSource.saveAddress(fakeAddress) } returns flowOf(Response.Success(Unit))

        val result = repository.saveAddress(fakeAddress)
        val value = result.collect { response ->
            assertThat(response is Response.Success, `is`(true))
        }
    }

    @Test
    fun `getAddresses should return list of addresses`() = runTest(testDispatcher) {
        val expected = listOf(fakeAddress)
        coEvery { remoteDataSource.getAddresses() } returns flowOf(Response.Success(expected))

        repository.getAddresses().collect { response ->
            assertThat((response as Response.Success).data.size, `is`(1))
        }
    }

    @Test
    fun `deleteAddress should return success`() = runTest(testDispatcher) {
        val deleteResponse = DeleteResponse(   deletedAddressId = "1", customerUserErrors = listOf(), userErrors = listOf())

        coEvery { remoteDataSource.deleteAddress("1") } returns flowOf(Response.Success(deleteResponse))

        repository.deleteAddress("1").collect { response ->
            assertThat((response as Response.Success).data.isSuccess, `is`(true))
        }
    }
}
