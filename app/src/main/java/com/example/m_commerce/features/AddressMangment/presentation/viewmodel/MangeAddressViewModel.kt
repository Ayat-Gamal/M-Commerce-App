package com.example.m_commerce.features.AddressMangment.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.AddressMangment.domain.usecases.GetCustomerAddressesUseCase
import com.example.m_commerce.features.AddressMangment.domain.usecases.GetDefaultAddressUseCase
import com.example.m_commerce.features.AddressMangment.domain.usecases.SaveAddressUseCase
import com.example.m_commerce.features.AddressMangment.domain.usecases.SetDefaultAddressUseCase
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddressViewModel @Inject constructor(
    private val saveAddressUseCase: SaveAddressUseCase,
    private val getAddressesUseCase: GetCustomerAddressesUseCase,
    private val getDefaultAddressUseCase: GetDefaultAddressUseCase,
    private val setDefaultAddressUseCase: SetDefaultAddressUseCase
) : ViewModel() {

    private val _customerAddresses = mutableStateOf<List<Storefront.MailingAddress>>(emptyList())
    val customerAddresses: State<List<Storefront.MailingAddress>> = _customerAddresses

    private val _defaultAddress =
        mutableStateOf(Storefront.MailingAddress())
    val defaultAddress: State<Storefront.MailingAddress> = _defaultAddress

    fun setDefaultAddressState(address: Storefront.MailingAddress) {
        _defaultAddress.value = address
    }


     fun saveAddress(address: Storefront.MailingAddressInput) {
        viewModelScope.launch {
            saveAddressUseCase(address)
        }
    }

    suspend fun getCustomerAddresses() {
        getAddressesUseCase() { addresses ->
            _customerAddresses.value = addresses
        }
    }

    suspend fun setDefaultAddress( id: ID) {
        setDefaultAddressUseCase( id)
    }

    suspend fun getDefaultAddress() {
        getDefaultAddressUseCase() { addresses ->
            _defaultAddress.value = addresses
        }
    }
}


