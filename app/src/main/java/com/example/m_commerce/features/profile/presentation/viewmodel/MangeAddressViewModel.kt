package com.example.m_commerce.features.profile.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.m_commerce.features.profile.domain.model.AddressItem

class MangeAddressViewModel : ViewModel() {
    val addresses = listOf(
        AddressItem("Home", "1901 Thornridge Cir. Shiloh, Hawaii 81063"),
        AddressItem("Office", "4517 Washington Ave. Manchester, Kentucky 39495"),
        AddressItem("Parent's House", "8502 Preston Rd. Inglewood, Maine 98380"),
        AddressItem("Friend's House", "2484 Royal Ln. Mesa, New Jersey 45463")
    )

    var selectedIndex = mutableStateOf(0)
}
