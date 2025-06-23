@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.m_commerce.features.profile.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.core.shared.components.CustomDialog
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.profile.presentation.components.currency.CurrencyList
import com.example.m_commerce.features.profile.presentation.components.currency.CurrencyListItem
import com.example.m_commerce.features.profile.presentation.viewmodel.CurrencyViewModel


@Composable
fun CurrencyUiLayout(
    navController: NavHostController,
    viewModel: CurrencyViewModel = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    var query by rememberSaveable { mutableStateOf("") }
    val state = viewModel.state
    val filteredCurrencies = state.currencies.filter {
        it.currencyName.contains(query, ignoreCase = true) ||
                it.currencyCode.contains(query, ignoreCase = true)
    }
    Scaffold(
        topBar = {
            Column() {
                DefaultTopBar(
                    title = "Choose Default Currency",
                    navController = navController,
                )
            }


        }
    ) { innerPadding ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }

            state.error.isNotEmpty() -> {
                // TODO: Handle error UI
            }

            else -> {
                var defaultCurrency by remember { mutableStateOf(viewModel.defaultCurrencyState.defaultCurrency) }
                var pendingCurrency by remember { mutableStateOf(defaultCurrency) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {

                    CustomDialog(
                        showDialog = showDialog,
                        title = "Confirmation",
                        message = "Are you sure you want to change the default currency?",
                        onConfirm = {
                            showDialog = false
                            defaultCurrency = pendingCurrency
                            defaultCurrency?.let { viewModel.saveDefaultCurrency(it) }
                        },
                        onDismiss = {
                            showDialog = false
                        }
                    )

                    InlineSearchBar(
                        query = query,
                        onQueryChange = { query = it },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Text(
                        text = "Your Default Currency",
                        modifier = Modifier.padding(16.dp)
                    )

                    defaultCurrency?.let {
                        CurrencyListItem(it, onCurrencyClick = {
                            print("we clicked on ${it.currencyName}")
                            showDialog = true
                            defaultCurrency = it
                        })
                    }
                    Text(
                        text = "Available Currencies",
                        modifier = Modifier.padding(16.dp)
                    )
                    CurrencyList(
                        filteredCurrencies,
                        modifier = Modifier.weight(1f), onCurrencyClick = {
                            showDialog = true
                            pendingCurrency = it
                        })
                }
            }
        }
    }

}


@Composable
fun InlineSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val brush = remember {
        Brush.linearGradient(
            colors = listOf(Teal, Color.Green, Color.Yellow, Color.Blue, Color.Magenta)
        )
    }
    OutlinedTextField(
        value = query,
        textStyle = TextStyle(brush = brush),
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        },
        placeholder = { Text("Search") },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = outlinedTextFieldColors(
            focusedBorderColor = Teal,
            unfocusedBorderColor = Teal,
            cursorColor = Teal
        )
    )
}
