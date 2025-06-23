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
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.m_commerce.features.AddressMangment.presentation.components.AddNewAddressButton
import com.example.m_commerce.features.AddressMangment.presentation.components.AddressCard
import com.example.m_commerce.features.AddressMangment.presentation.viewmodel.AddressViewModel
import com.shopify.buy3.Storefront
import kotlinx.coroutines.launch


@SuppressLint("UnrememberedMutableState")
@Composable
fun ManageAddressScreenUi(
    navController: NavHostController,
    viewModel: AddressViewModel = hiltViewModel()
) {

    var showDialog by remember { mutableStateOf(false) }
    var pendingAddress by remember { mutableStateOf(Storefront.MailingAddress()) }
    val defaultAddress by viewModel.defaultAddress
    var selectedIndex = remember { mutableStateOf(0) }

    val navBackStackEntry = navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry.value) {
        viewModel.getCustomerAddresses()
        viewModel.getDefaultAddress()
    }

    Scaffold(topBar = {
        DefaultTopBar(title = "Manage Address", navController = navController)
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
        ) {
            CustomDialog(
                showDialog = showDialog,
                title = "Confirmation",
                message = "Are you sure you want to change to the default Address?",
                onConfirm = {
                    showDialog = false
                    viewModel.viewModelScope.launch {

                    viewModel.setDefaultAddress(
                        id = pendingAddress.id,
                    )
                    }
                    viewModel.setDefaultAddressState(pendingAddress)
                    },
                onDismiss = {
                    showDialog = false
                }
            )
            Text(
                text = "Default Address ",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            AddressCard(
                item = defaultAddress,
                isSelected = false,
                onLongSelect = { item ->
                    showDialog  }, onCheck = {  },
                onDelete = { Log.i("TAG", "deleted: ") },
                hideDeleteIconFlag = true
            )

        }

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(500.dp)
            ) {
                itemsIndexed(viewModel.customerAddresses.value) { index, item ->
                    AddressCard(
                        item = item,
                        isSelected = selectedIndex.value == index,
                        onLongSelect = { address ->
                            showDialog = true
                            pendingAddress = address
                        },
                        onCheck = { selectedIndex.value = index },
                        onDelete = { println("Deleted") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            AddNewAddressButton(onClick = {
                navController.navigate(AppRoutes.MapScreen)
            })

            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.height(32.dp))
        }

    }
}
