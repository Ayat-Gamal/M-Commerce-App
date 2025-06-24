package com.example.m_commerce.features.AddressMangment.presentation.screen

import android.location.Geocoder
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.mutableStateMapOf
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
import com.example.m_commerce.features.AddressMangment.domain.entity.Address
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
    var city by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }

    val fieldErrors = remember {
        mutableStateMapOf<String, String>()
    }

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addressList = geocoder.getFromLocation(lat, lng, 1)
            addressList?.firstOrNull()?.let { address ->
                completeAddress = address.getAddressLine(0) ?: ""
                city = address.locality ?: ""
                country = address.countryName ?: ""
                zipCode = address.postalCode ?: ""
            }
        } catch (e: Exception) {
            Log.e("Geocoder", "Failed to get address from location", e)
        }
    }

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Address Information",
                navController = navController
            )
        }
    ) { padding ->
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
                value = addressType,
                onValueChange = {
                    addressType = it
                    fieldErrors.remove("title")
                },
                isError = fieldErrors.containsKey("title"),
                errorMessage = fieldErrors["title"] ?: ""
            )

            CustomOutlinedField(
                label = "Complete Address *",
                value = completeAddress,
                maxLines = 3,
                onValueChange = {
                    completeAddress = it
                    fieldErrors.remove("address")
                },
                isError = fieldErrors.containsKey("address"),
                errorMessage = fieldErrors["address"] ?: ""
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomOutlinedField(
                    label = "City *",
                    value = city,
                    onValueChange = {
                        city = it
                        fieldErrors.remove("city")
                    },
                    isError = fieldErrors.containsKey("city"),
                    errorMessage = fieldErrors["city"] ?: "",
                    modifier = Modifier.weight(1f)
                )

                CustomOutlinedField(
                    label = "Country *",
                    value = country,
                    onValueChange = {
                        country = it
                        fieldErrors.remove("country")
                    },
                    isError = fieldErrors.containsKey("country"),
                    errorMessage = fieldErrors["country"] ?: "",
                    modifier = Modifier.weight(1f)
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomOutlinedField(
                    label = "Zip Code *",
                    value = zipCode,
                    keyboardType = KeyboardType.Number,
                    onValueChange = {
                        zipCode = it
                        fieldErrors.remove("zip")
                    },
                    isError = fieldErrors.containsKey("zip"),
                    errorMessage = fieldErrors["zip"] ?: "",
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
                    fieldErrors.remove("phone")
                },
                isError = fieldErrors.containsKey("phone"),
                errorMessage = fieldErrors["phone"] ?: ""
            )

            Spacer(modifier = Modifier.weight(1f))

            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                text = "Save Address",
                backgroundColor =  Teal,
                textColor = White,
                cornerRadius = 12,
                onClick = {
                    val requiredFields = mapOf(
                        "title" to addressType,
                        "address" to completeAddress,
                        "city" to city,
                        "country" to country,
                        "zip" to zipCode,
                        "phone" to phone
                    )

                    fieldErrors.clear()
                    var isValid = true

                    requiredFields.forEach { (field, value) ->
                        if (value.isBlank()) {
                            fieldErrors[field] = "This field is required"
                            isValid = false
                        }
                    }

                    if (isValid) {
                        viewModel.saveAddress(
                            addressType = addressType,
                            address1 = completeAddress,
                            city = city,
                            country = country,
                            zip = zipCode,
                            phone = phone
                        )
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

