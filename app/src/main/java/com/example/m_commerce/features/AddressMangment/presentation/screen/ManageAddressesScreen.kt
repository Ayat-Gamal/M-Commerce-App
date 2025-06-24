package com.example.m_commerce.features.AddressMangment.presentation.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.core.shared.components.CustomDialog
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.AddressMangment.domain.entity.Address
import com.example.m_commerce.features.AddressMangment.presentation.components.AddNewAddressButton
import com.example.m_commerce.features.AddressMangment.presentation.components.AddressCard
import com.example.m_commerce.features.AddressMangment.presentation.ui_states.DeleteState
import com.example.m_commerce.features.AddressMangment.presentation.viewmodel.AddressViewModel
import com.shopify.buy3.Storefront
import kotlinx.coroutines.launch


@SuppressLint("UnrememberedMutableState")
@Composable
fun ManageAddressScreenUi(
    navController: NavHostController,
    viewModel: AddressViewModel = hiltViewModel()
) {
    var showDefaultAddressDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var pendingAddress by remember { mutableStateOf<Address?>(null) }
    val addresses by viewModel.addresses
    val defaultAddress by viewModel.defaultAddress
    val isLoading by viewModel.isLoading
    val deleteState by viewModel.deleteState

    LaunchedEffect(Unit) {
        viewModel.loadAddresses()
    }

    LaunchedEffect(deleteState) {
        when (deleteState) {
            is DeleteState.Success -> {
                viewModel.resetDeleteState()
            }
            is DeleteState.Error -> {
                viewModel.resetDeleteState()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Manage Address",
                navController = navController,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
        ) {
            pendingAddress?.let { address ->
                CustomDialog(
                    showDialog = showDefaultAddressDialog,
                    title = "Confirmation",
                    message = "Set this as your default address?",
                    onConfirm = {
                        showDefaultAddressDialog = false
                        viewModel.setDefaultAddress(address.id)
                    },
                    onDismiss = { showDefaultAddressDialog = false }
                )
            }

            pendingAddress?.let { address ->
                CustomDialog(
                    showDialog = showDeleteDialog,
                    title = "Delete Address",
                    message = "Are you sure you want to delete this address?",
                    onConfirm = {
                        showDeleteDialog = false
                        viewModel.deleteAddress(address.id)
                        pendingAddress = null
                    },
                    onDismiss = {
                        showDeleteDialog = false
                        pendingAddress = null
                    }
                )
            }

            Text(
                text = "Default Address",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            when {
                isLoading && defaultAddress == null -> {
                    CircularProgressIndicator(
                    )
                }
                defaultAddress != null -> {
                    defaultAddress?.let { address ->
                        AddressCard(
                            address = address,
                            isDefault = true,
                            onLongPress = {
                                pendingAddress = address
                                showDefaultAddressDialog = true
                            },
                            onSelect = null,
                            onDelete = null
                        )
                    }
                }
                else -> {
                    Text(
                        text = "No default address set",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(16.dp))

            when {
                isLoading && addresses.isEmpty() -> {
                    CircularProgressIndicator(
                    )
                }
                addresses.isEmpty() -> {
                    Text(
                        text = "No addresses saved",
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(addresses) { address ->
                            AddressCard(
                                address = address,
                                isDefault = false,
                                onLongPress = {
                                    pendingAddress = address
                                    showDefaultAddressDialog = true
                                },
                                onSelect = {},
                                onDelete = {
                                    pendingAddress = address
                                    showDeleteDialog = true
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            AddNewAddressButton(
                onClick = { navController.navigate(AppRoutes.MapScreen) },)
        }
    }
}