
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.dividerGray
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.cart.presentation.CartUiState
import com.example.m_commerce.features.cart.presentation.components.CartItemCard
import com.example.m_commerce.features.cart.presentation.components.CartReceipt
import com.example.m_commerce.features.cart.presentation.viewmodel.CartViewModel

@Composable
fun CartScreenUI(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.getCartById()
    }

    Scaffold(
        modifier = modifier.background(Teal),
        topBar = {
            DefaultTopBar(title = "Cart", navController = null, titleCentered = true)
        },
        bottomBar = {
            if (uiState is CartUiState.Success) {
                CartReceipt(
                    paddingValues,
                    // cart = (uiState as CartUiState.Success).cart
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Background),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is CartUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = Teal
                    )
                }

                is CartUiState.Success -> {
                    val cart = (uiState as CartUiState.Success).cart
                    CartContent(
                        cartLines = cart.lines,
                        viewModel = viewModel
                    )
                }

                is CartUiState.Empty -> {
                    Text(
                        text = "Your cart is empty",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                }

                is CartUiState.Guest -> {
                    Text(
                        text = "Please login to view your cart",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                }

                is CartUiState.NoNetwork -> {
                    Text(
                        text = "No internet connection",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                }

                is CartUiState.Error -> {
                    Text(
                        text = "Error loading cart",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                }

                CartUiState.Search -> {
                    // TODO: Handle search state if needed
                }
            }
        }
    }
}


@Composable
fun CartContent(cartLines: List<ProductVariant>, viewModel: CartViewModel) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.4f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cartLines) { line ->
                CartItemCard(
                    prodct = line,
                    onIncrease = {
                        viewModel.increaseQuantity(line.lineId) },
                    onDecrease = {
                        viewModel.decreaseQuantity(line.lineId) },
                    onRemove = {
                        viewModel.removeLine(line.lineId) }
                )

                if (cartLines.indexOf(line) < cartLines.size - 1) {
                    Divider(
                        color = dividerGray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}


//package com.example.m_commerce.features.cart.presentation.screen
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.Divider
//import androidx.compose.material3.Scaffold
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.example.m_commerce.config.theme.Background
//import com.example.m_commerce.config.theme.Teal
//import com.example.m_commerce.config.theme.dividerGray
//import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
//import com.example.m_commerce.features.cart.presentation.components.CartItemCard
//import com.example.m_commerce.features.cart.presentation.components.CartReceipt
//import com.example.m_commerce.features.cart.presentation.viewmodel.CartViewModel
//
//@Composable
//fun CartScreenUI(paddingValues: PaddingValues, modifier: Modifier = Modifier, viewModel: CartViewModel = hiltViewModel()) {
//
//
//    var quantity by rememberSaveable { mutableStateOf(1) }
//
//    viewModel.getCartById()
//
//    Scaffold(
//        modifier = modifier.background(Teal),
//        topBar = {
//            DefaultTopBar(title = "Cart", navController = null, titleCentered = true)
//        },
//        bottomBar = {
//            CartReceipt(paddingValues)
//        }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .padding(padding)
//                .fillMaxSize()
//                .background(Background)
//
//        ) {
//            LazyColumn(
//                modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.4f),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                //handel the item to be cartLines
//                items(10) { index ->
//                    CartItemCard(
//                        quantity = quantity,
//                        onIncrease = { quantity++ },
//                        onDecrease = { if (quantity > 1) quantity-- }
//                    )
//                    if (index < 9) {
//                        Divider(
//                            color = dividerGray,
//                            thickness = 1.dp,
//                            modifier = Modifier.padding(horizontal = 16.dp)
//                        )
//                    }
//                }
//            }
//
//        }
//    }
//}
//
