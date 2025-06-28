package com.example.m_commerce.features.AddressMangment.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.core.utils.NetworkManager
import com.example.m_commerce.features.AddressMangment.domain.entity.Address
import com.example.m_commerce.features.AddressMangment.domain.entity.Response
import com.example.m_commerce.features.AddressMangment.domain.usecases.DeleteAddressUseCase
import com.example.m_commerce.features.AddressMangment.domain.usecases.GetAddressesUseCase
import com.example.m_commerce.features.AddressMangment.domain.usecases.GetDefaultAddressUseCase
import com.example.m_commerce.features.AddressMangment.domain.usecases.SaveAddressUseCase
import com.example.m_commerce.features.AddressMangment.domain.usecases.SetDefaultAddressUseCase
import com.example.m_commerce.features.AddressMangment.presentation.ui_states.DeleteState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val saveAddressUseCase: SaveAddressUseCase,
    private val getAddressesUseCase: GetAddressesUseCase,
    private val getDefaultAddressUseCase: GetDefaultAddressUseCase,
    private val setDefaultAddressUseCase: SetDefaultAddressUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    private val firebaseAuth: FirebaseAuth,
    private val networkManager: NetworkManager,
    ) : ViewModel() {

    private val _addresses = mutableStateOf<List<Address>>(emptyList())
    val addresses: State<List<Address>> = _addresses

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _defaultAddress = mutableStateOf<Address?>(null)
    val defaultAddress: State<Address?> = _defaultAddress


    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _saveSuccess = mutableStateOf(false)
    val saveSuccess: State<Boolean> = _saveSuccess

    private val _deleteState = mutableStateOf<DeleteState>(DeleteState.Idle)
    val deleteState: State<DeleteState> = _deleteState

    init {
        loadAddresses()
    }


    fun saveAddress(
        addressType: String,
        address1: String,
        city: String,
        country: String,
        zip: String,
        phone: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _saveSuccess.value = false
            try {
                val user = firebaseAuth.currentUser ?: throw Exception("User not logged in")

                val address = Address(
                    id = user.uid,
                    firstName = user.displayName ?: "Guest",
                    lastName = "",
                    address1 = address1,
                    city = city,
                    country = country,
                    zip = zip,
                    address2 = addressType,
                    phone = phone
                )

                saveAddressUseCase(address).collect { response ->
                    when (response) {
                        is Response.Loading -> _isLoading.value = true
                        is Response.Success -> {
                            _isLoading.value = false
                            _saveSuccess.value = true
                            loadAddresses()
                        }

                        is Response.Error -> {
                            _errorMessage.value = response.message
                            _isLoading.value = false
                        }
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Save failed: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun setDefaultAddress(addressId: String) {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            setDefaultAddressUseCase(addressId).collect { response ->
                when (response) {
                    is Response.Loading -> _isLoading.value = true
                    is Response.Success -> {
                        _isLoading.value = false
                        getDefaultAddress()
                    }

                    is Response.Error -> {
                        _errorMessage.value = response.message
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun getDefaultAddress() = viewModelScope.launch {
        getDefaultAddressUseCase().collect { response ->
            Log.i("TAG", "getDefaultAddress ${response}: ")
            when (response) {
                is Response.Success -> _defaultAddress.value = response.data
                is Response.Error -> _errorMessage.value = response.message
                Response.Loading -> _isLoading.value = true
            }
        }
    }


    fun deleteAddress(addressId: String) {
        viewModelScope.launch {
            _deleteState.value = DeleteState.Loading

            deleteAddressUseCase(addressId).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _deleteState.value = DeleteState.Loading
                        _isLoading.value = true
                    }

                    is Response.Success -> {
                        _isLoading.value = false
                        if (response.data.isSuccess) {
                            _deleteState.value = DeleteState.Success(
                                response.data.deletedAddressId ?: addressId
                            )
                            loadAddresses()
                        } else {
                            val errorMessage = (response.data.customerUserErrors +
                                    response.data.userErrors)
                                .joinToString { it.message ?: "Unknown error" }
                            _deleteState.value = DeleteState.Error(errorMessage)
                        }
                    }

                    is Response.Error -> {
                        _isLoading.value = false
                        _deleteState.value = DeleteState.Error(
                            response.message ?: "Failed to delete address"
                        )
                    }
                }
            }
        }
    }

    fun loadAddresses() {
        if (!networkManager.isNetworkAvailable()) {
            _addresses.value = emptyList()
            return
        }
        viewModelScope.launch {
            _isLoading.value = true

            getAddressesUseCase().collect { addressesResponse ->
                when (addressesResponse) {
                    is Response.Success -> {
                        val allAddresses = addressesResponse.data

                        getDefaultAddressUseCase().collect { defaultResponse ->
                            when (defaultResponse) {
                                is Response.Success -> {
                                    val defaultAddress = defaultResponse.data
                                    _defaultAddress.value = defaultAddress

                                    val filtered = allAddresses
                                        .filter { it.address1 != defaultAddress.address1 }
                                        .distinctBy { it.address1 }

                                    _addresses.value = filtered
                                }

                                is Response.Error -> _errorMessage.value = defaultResponse.message
                                Response.Loading -> _isLoading.value = true
                            }
                        }

                        _isLoading.value = false
                    }

                    is Response.Error -> {
                        _deleteState.value = DeleteState.Error(
                            addressesResponse.message ?: "Failed to load addresses"
                        )
                        _isLoading.value = false
                    }

                    Response.Loading -> _isLoading.value = true
                }
            }
        }


    }

    fun resetDeleteState() {
        _deleteState.value = DeleteState.Idle
    }

    fun clearMessages() {
        _errorMessage.value = null
        _saveSuccess.value = false
    }

    fun isConnected() = networkManager.isNetworkAvailable()
}

