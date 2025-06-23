package com.example.m_commerce.features.AddressMangment.presentation.screen

import android.location.Geocoder
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.AddressMangment.presentation.viewmodel.AddressViewModel
import com.shopify.buy3.Storefront
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun AddAddressScreen(
    navController: NavHostController,
    lat: Double,
    lng: Double,
    viewModel: AddressViewModel = hiltViewModel()
) {
    var addressType by remember { mutableStateOf("Home") }
    var completeAddress by remember { mutableStateOf("") }
    var floor by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }

    var titleError by remember { mutableStateOf("") }
    var completeAddressError by remember { mutableStateOf("") }
    var cityError by remember { mutableStateOf("") }
    var countryError by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf("") }
    var zipCodeError by remember { mutableStateOf("") }

    val localContext = LocalContext.current

    LaunchedEffect(Unit) {
        val geocoder = Geocoder(localContext, Locale.getDefault())
        val addressList = geocoder.getFromLocation(lat, lng, 1)
        if (!addressList.isNullOrEmpty()) {
            val address = addressList[0]
            completeAddress = address.getAddressLine(0) ?: ""
            city = address.locality ?: ""
            country = address.countryName ?: ""
            zipCode = address.postalCode ?: ""
        }
    }

    Scaffold(
        topBar = {
            DefaultTopBar(title = "Address Information", navController = navController)
        }
    ) { padding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Background)
                .padding(horizontal = 16.dp)
                .padding(padding)
        ) {
            CustomOutlinedField(
                label = "Title *",
                value = title,
                onValueChange = {
                    title = it
                    titleError = ""
                },
                isError = titleError.isNotEmpty(),
                errorMessage = titleError
            )

            CustomOutlinedField(
                label = "Complete Address *",
                value = completeAddress,
                maxLines = 4,
                onValueChange = {
                    completeAddress = it
                    completeAddressError = ""
                },
                isError = completeAddressError.isNotEmpty(),
                errorMessage = completeAddressError
            )

            CustomOutlinedField(
                label = "City *",
                value = city,
                onValueChange = {
                    city = it
                    cityError = ""
                },
                isError = cityError.isNotEmpty(),
                errorMessage = cityError
            )

            CustomOutlinedField(
                label = "Country *",
                value = country,
                onValueChange = {
                    country = it
                    countryError = ""
                },
                isError = countryError.isNotEmpty(),
                errorMessage = countryError
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomOutlinedField(
                    label = "Zip Code *",
                    value = zipCode,
                    keyboardType = KeyboardType.Number,
                    onValueChange = {
                        zipCode = it
                        zipCodeError = ""
                    },
                    isError = zipCodeError.isNotEmpty(),
                    errorMessage = zipCodeError,
                    modifier = Modifier.weight(1f)
                )

                CustomOutlinedField(
                    label = "Floor",
                    value = floor,
                    onValueChange = { floor = it },
                    modifier = Modifier.weight(1f)
                )
            }

            CustomOutlinedField(
                label = "Phone *",
                value = phone,
                keyboardType = KeyboardType.Phone,
                onValueChange = {
                    phone = it
                    phoneError = ""
                },
                isError = phoneError.isNotEmpty(),
                errorMessage = phoneError
            )

            Spacer(modifier = Modifier.weight(1f))

            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                text = "Save Address",
                backgroundColor = Teal,
                textColor = White,
                height = 50,
                cornerRadius = 12,
                onClick = {
                    fun String.isValid() = this.trim().isNotEmpty()

                    val isTitleValid = title.isValid().also {
                        titleError = if (!it) "Title must not be empty" else ""
                    }

                    val isAddressValid = completeAddress.isValid().also {
                        completeAddressError = if (!it) "Address must not be empty" else ""
                    }

                    val isCityValid = city.isValid().also {
                        cityError = if (!it) "City must not be empty" else ""
                    }

                    val isCountryValid = country.isValid().also {
                        countryError = if (!it) "Country must not be empty" else ""
                    }

                    val isPhoneValid = phone.isValid().also {
                        phoneError = if (!it) "Phone must not be empty" else ""
                    }

                    val isZipValid = zipCode.isValid().also {
                        zipCodeError = if (!it) "Zip code must not be empty" else ""
                    }

                    val allValid = listOf(
                        isTitleValid,
                        isAddressValid,
                        isCityValid,
                        isCountryValid,
                        isPhoneValid,
                        isZipValid
                    ).all { it }

                    if (allValid) {
                        val address = Storefront.MailingAddressInput().apply {
                            address1 = completeAddress
                            city = city
                            country = country
                            firstName = title
                            lastName = city
                            zip = zipCode
                        }

                        viewModel.viewModelScope.launch {
                            viewModel.saveAddress(address)
                            viewModel.getCustomerAddresses()
                        }

                        navController.navigate(AppRoutes.ManageAddressScreen) {
                            popUpTo(AppRoutes.ManageAddressScreen) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
@Composable
fun CustomOutlinedField(
    label: String,
    value: String,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String = ""
) {
    Column(modifier = modifier.padding(vertical = 6.dp)) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text("Enter $label") },
            shape = RoundedCornerShape(12.dp),
            maxLines = maxLines,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Teal,
                focusedLabelColor = Teal,
                errorBorderColor = Color.Red,
                cursorColor = Teal
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 2.dp)
            )
        }
    }
}

