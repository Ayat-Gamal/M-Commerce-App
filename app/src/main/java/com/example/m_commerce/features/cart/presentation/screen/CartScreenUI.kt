
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.dividerGray
import com.example.m_commerce.core.shared.components.CustomDialog
import com.example.m_commerce.core.shared.components.Empty
import com.example.m_commerce.core.shared.components.GuestMode
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.cart.presentation.CartUiState
import com.example.m_commerce.features.cart.presentation.UiEvent
import com.example.m_commerce.features.cart.presentation.components.CartItemCard
import com.example.m_commerce.features.cart.presentation.components.CartReceipt
import com.example.m_commerce.features.cart.presentation.viewmodel.CartViewModel
import com.example.m_commerce.features.profile.presentation.viewmodel.CurrencyViewModel
import com.stripe.android.paymentsheet.PaymentSheet

@Composable
fun CartScreenUI(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel(),
    currencyViewModel: CurrencyViewModel = hiltViewModel(),
    paymentSheet: PaymentSheet
) {
    val uiState by cartViewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        cartViewModel.snackBarFlow.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
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
                    viewModel = cartViewModel,
                    currencyViewModel = currencyViewModel,
                    paymentSheet = paymentSheet
                )
            }
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState )
        }

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
                        viewModel = cartViewModel,
                        currencyViewModel = currencyViewModel,
                    )
                }

                is CartUiState.Empty -> {
                    Empty(
                        message = "Your Cart is empty"
                    )
                }

                is CartUiState.Guest -> {
                    GuestMode(navController, "Cart", Icons.Default.ShoppingCart)
                }

                is CartUiState.NoNetwork -> {
                    Text(
                        text = "No internet connection",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                }

                is CartUiState.Error -> {
                    Text(
                        text = "We will come back Soon ",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                }


            }
        }
    }
}



@Composable
fun CartContent(
    cartLines: List<ProductVariant>,
    viewModel: CartViewModel,
    currencyViewModel: CurrencyViewModel,
) {
    var showDialog by remember { mutableStateOf(false) }
    var pendingRemovalLine: ProductVariant? by remember { mutableStateOf(null) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomDialog(
            showDialog = showDialog,
            title = "Confirm Change",
            message = "Are you sure you want to remove this item?",
            onConfirm = {
                showDialog = false
                pendingRemovalLine?.let { viewModel.removeLine(it.lineId) }
            },
            onDismiss = {
                showDialog = false
            }
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.4f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cartLines) { line ->
                CartItemCard(
                    product = line,
                    onIncrease = {
                        viewModel.increaseQuantity(line.lineId)
                    },
                    onDecrease = {
                        viewModel.decreaseQuantity(line.lineId)
                    },
                    onRemove = {
                        pendingRemovalLine = line
                        showDialog = true
                    },
                    currencyViewModel
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

