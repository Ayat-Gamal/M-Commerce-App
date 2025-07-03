package com.example.m_commerce.features.product.presentation.screen

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.core.shared.components.Failed
import com.example.m_commerce.core.shared.components.NetworkImage
import com.example.m_commerce.core.shared.components.NoNetwork
import com.example.m_commerce.core.shared.components.TagWithText
import com.example.m_commerce.core.shared.components.screen_cases.Loading
import com.example.m_commerce.core.utils.extentions.capitalizeFirstLetters
import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.product.presentation.ProductUiState
import com.example.m_commerce.features.product.presentation.ProductViewModel
import com.example.m_commerce.features.product.presentation.components.BottomBar
import com.example.m_commerce.features.product.presentation.components.DotsIndicator
import com.example.m_commerce.features.product.presentation.components.QuantitySelector
import com.example.m_commerce.features.product.presentation.components.TransParentTopBar
import com.example.m_commerce.features.product.presentation.components.VariantHeaderText
import com.example.m_commerce.features.product.presentation.components.VariantValueText
import com.example.m_commerce.features.profile.presentation.viewmodel.CurrencyViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


@Composable
fun ProductDetailsScreenUI(
    productId: String,
    navController: NavHostController,
    viewModel: ProductViewModel = hiltViewModel(),
    currencyViewModel: CurrencyViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val snackBarHostState = remember { SnackbarHostState() }
    var selectedSize by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf("") }
    val quantity = remember { mutableStateOf(1) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val user = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(uiState) {
        if (uiState is ProductUiState.Success) {
            val product = (uiState as ProductUiState.Success).product
            if (selectedSize.isEmpty() && product.sizes.isNotEmpty()) {
                selectedSize = product.sizes[0]
            }
            if (selectedColor.isEmpty() && product.colors.isNotEmpty()) {
                selectedColor = product.colors[0]
            }
            isFavorite = (uiState as ProductUiState.Success).isFavorite
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getProductById(productId)
        viewModel.message.collect { event ->
            scope.launch {
                snackBarHostState.currentSnackbarData?.dismiss()

                val result = snackBarHostState.showSnackbar(
                    message = event.message,
                    actionLabel = event.actionLabel,
                    duration = SnackbarDuration.Short
                )

                if (result == SnackbarResult.ActionPerformed) {
                    event.onAction?.invoke()
                    isFavorite = true
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.cartOperationResult.collect {
            isLoading.value = false
        }
    }

    Scaffold(
        topBar = {
            if (uiState is ProductUiState.Success) {
                TransParentTopBar(navController, user, isFavorite) {
                    toggleFavorite(viewModel, isFavorite, productId).also {
                        isFavorite = !isFavorite
                    }
                }
            } else {
                TransParentTopBar(navController, null, false) {}
            }
        },
        bottomBar = {
            if (uiState is ProductUiState.Success) {
                val product = (uiState as ProductUiState.Success).product
                BottomBar(product.price, isLoading.value) {

                    if (!product.availableForSale) {
                        scope.launch {
                            snackBarHostState.showSnackbar("Product is out of stock")
                        }
                        return@BottomBar
                    }

                    if (user != null) {
                        isLoading.value = true
                        val variantId = viewModel.findSelectedVariantId(
                            product.variants,
                            selectedSize,
                            selectedColor
                        )
                        variantId?.let { viewModel.addToCart(it, quantity.value) }
                    } else {
                        scope.launch {
                            snackBarHostState.showSnackbar("Please sign in to add items to your cart")
                        }
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->
        when (uiState) {
            is ProductUiState.Error -> Failed((uiState as ProductUiState.Error).message)
            is ProductUiState.Loading -> Loading()

            is ProductUiState.NoNetwork -> NoNetwork()
            is ProductUiState.Success -> {
                val product = (uiState as ProductUiState.Success).product
                LoadData(
                    product = product,
                    scrollState = scrollState,
                    paddingValues = paddingValues,
                    selectedSize = selectedSize,
                    onSizeSelected = { selectedSize = it },
                    selectedColor = selectedColor,
                    quantity = quantity,
                    onColorSelected = { selectedColor = it }
                )
            }
        }
    }
}


@Composable
fun LoadData(
    product: Product,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    selectedSize: String,
    onSizeSelected: (String) -> Unit,
    selectedColor: String,
    quantity: MutableState<Int>,
    onColorSelected: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding())
            .verticalScroll(scrollState)
    ) {
        val pagerState = rememberPagerState { product.images.size }

        // Image Carousel
        Box(contentAlignment = Alignment.BottomCenter) {
            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) { page ->
                NetworkImage(
                    modifier = Modifier.fillMaxWidth(),
                    url = product.images[page],
//                    contentScale = ContentScale.FillWidth
                )
            }
            DotsIndicator(
                totalDots = product.images.size,
                selectedIndex = pagerState.currentPage,
                modifier = Modifier
                    .padding(bottom = 70.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.4f))
            )
        }

        // Main Content Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-50).dp)
                .background(White, shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp))
                .padding(vertical = 24.dp)
        ) {
            // Title & Category
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), verticalAlignment = Alignment.Top
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        product.title.capitalizeEachWord(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black.copy(0.8f)
                    )
                    Spacer(Modifier.height(8.dp))
                    TagWithText(product.category.capitalizeFirstLetters())
                }

                // Quantity Box
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    QuantitySelector(quantity.value) { quantity.value = it }
                    Text(
                        if (product.availableForSale) "In stock" else "Out of Stock",
                        fontSize = 14.sp,
                        color = if (product.availableForSale) Color(0xFF4CAF50) else Color(
                            0xFFF44336
                        ),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Color
            if (product.colors.isNotEmpty()) {
                Row(Modifier.padding(horizontal = 16.dp)) {
                    VariantHeaderText("Color")
                    Spacer(Modifier.width(4.dp))
                    VariantValueText(selectedColor.capitalizeFirstLetters())
                }
                Spacer(Modifier.height(12.dp))
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
                    items(product.colors) { colorName ->
                        val color = parseColorFromName(colorName)
                        val isSelected = selectedColor == colorName
                        Box(
                            modifier = Modifier
                                .size(35.dp)
                                .shadow(4.dp, shape = CircleShape, clip = false)
                                .background(color = color, shape = CircleShape)
                                .clip(CircleShape)
                                .clickable { onColorSelected(colorName) },
                        ) {
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = null,
                                    tint = if (color == White) Color.DarkGray else White,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
            LaunchedEffect(selectedColor) {
                Log.i("TAG", "LoadData: $selectedColor")
            }

            // Size
            if (product.sizes.isNotEmpty()) {
                Row(Modifier.padding(horizontal = 16.dp)) {
                    VariantHeaderText("Size")
                    Spacer(Modifier.width(4.dp))
                    VariantValueText(selectedSize)
                }
                Spacer(Modifier.height(8.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(product.sizes) { size ->
                        val isSelected = selectedSize == size
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                // TODO find variant
                                // TODO check quantity and set if is in stock
                                onSizeSelected(size)
                            },
                            label = {
                                Text(
                                    text = size.uppercase(),
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(vertical = 6.dp)
                                )
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = White,
                                selectedContainerColor = Teal,
                                labelColor = Color.DarkGray,
                                selectedLabelColor = White
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = Color.LightGray,
                                borderWidth = 1.dp,
                                selectedBorderColor = Color.Transparent
                            )
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                VariantHeaderText("Description")
                Spacer(Modifier.height(8.dp))
                Text(text = product.description, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

fun toggleFavorite(viewModel: ProductViewModel, isFavorite: Boolean, productId: String) {
    if (!viewModel.isConnected()) {
        return
    }

    if (isFavorite) {
        viewModel.deleteProductFromWishlist(productId)
    } else {
        viewModel.addProductToWishlist(productId)
    }
}


fun parseColorFromName(name: String): Color {
    Log.i("TAG", "parseColorFromName: $name")
    return when (name.lowercase()) {
        "black" -> Color.Black
        "white" -> White
        "beige" -> Color(0xFFF5F5DC)
        "light_brown" -> Color(0xFF8B5E3C)
        "burgandy" -> Color(0xFF800020)
        "blue" -> Color.Blue
        "red" -> Color.Red
        "gray" -> Color.Gray
        "yellow" -> Color.Yellow
        "Army Green" -> Color(0xFF716c4e)
        else -> try {
            Color(android.graphics.Color.parseColor(name))
        } catch (e: Exception) {
            Color(0xFF716c4e)
        }
    }
}


fun String.capitalizeEachWord(): String =
    this.lowercase()
        .split(" ")
        .joinToString(" ") { word ->
            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }

