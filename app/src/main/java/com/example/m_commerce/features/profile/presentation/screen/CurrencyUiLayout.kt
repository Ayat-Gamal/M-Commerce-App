@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.m_commerce.features.profile.presentation.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.core.shared.components.CustomDialog
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.core.shared.components.screen_cases.Loading
import com.example.m_commerce.features.profile.presentation.viewmodel.CurrencyViewModel

@Composable
fun CurrencyScreenUi(
    navController: NavHostController,
    viewModel: CurrencyViewModel = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    var query by rememberSaveable { mutableStateOf("") }
    var selectedCurrencyCode by remember { mutableStateOf<String?>(null) }

    val state = viewModel.state

    Scaffold(
        topBar = {
            Column {
                DefaultTopBar(
                    title = "Choose Default Currency",
                    navController = navController
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
        ) {
            when {
                state.isLoading -> {
//                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                    Loading()
                }

                state.error.isNotEmpty() -> {
                    Text("Error: ${state.error}", Modifier.align(Alignment.Center))
                }

                else -> {
                    val defaultCode = viewModel.defaultCurrencyCode
                    val symbolsMap = state.currencies.firstOrNull()?.symbols ?: emptyMap()

                    val filtered = symbolsMap.filter { (code, name) ->
                        code.contains(query, ignoreCase = true) ||
                                name.contains(query, ignoreCase = true)
                    }

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        CustomDialog(
                            showDialog = showDialog,
                            title = "Confirm Change",
                            message = "Set $selectedCurrencyCode as default currency?",
                            onConfirm = {
                                showDialog = false
                                selectedCurrencyCode?.let { viewModel.saveDefaultCurrency(it) }
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
                            text = "Your Default: ${defaultCode ?: "None"}", fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )

                        Text(
                            text = "Available Currencies",
                            modifier = Modifier.padding(16.dp),
                            fontWeight = FontWeight.Bold
                        )

                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(filtered.entries.toList()) {
                                (code, name) ->
                                Text(
                                    text = "$code – $name",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedCurrencyCode = code
                                            showDialog = true
                                        }
                                        .padding(12.dp)
                                )
                            }
                        }
                    }
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
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Teal,
            unfocusedBorderColor = Teal,
            cursorColor = Teal
        )
    )
}
