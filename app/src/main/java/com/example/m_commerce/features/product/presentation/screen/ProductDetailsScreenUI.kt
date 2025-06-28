package com.example.m_commerce.features.product.presentation.screen

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.core.shared.components.Failed
import com.example.m_commerce.core.shared.components.NetworkImage
import com.example.m_commerce.core.shared.components.NoNetwork
import com.example.m_commerce.core.shared.components.default_top_bar.BackButton
import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.product.presentation.ProductUiState
import com.example.m_commerce.features.product.presentation.ProductViewModel
import com.example.m_commerce.features.product.presentation.components.VariantHeaderText
import com.example.m_commerce.features.product.presentation.components.VariantValueText
import com.example.m_commerce.features.profile.presentation.viewmodel.CurrencyViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

@Composable
fun BottomBar(price: String, isLoading: Boolean, onAddToCart: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text("$", color = Teal, fontWeight = FontWeight.Bold, fontSize = 30.sp)
            Text(
                text = price,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(Modifier.width(16.dp))
        CustomButton(
            onClick = onAddToCart,
            text = "Add to Cart",
            modifier = Modifier.fillMaxWidth(),
            isLoading = isLoading,
            fontSize = 18,
            contentPadding = PaddingValues(horizontal = 16.dp),
            isCart = true
        )
    }
}

@Composable
fun ProductDetailsScreenUI(
    snackBarHostState: SnackbarHostState,
    productId: String,
    navController: NavHostController,
    viewModel: ProductViewModel = hiltViewModel(),
    currencyViewModel: CurrencyViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var selectedSize by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf("") }
    var quantity = remember { mutableStateOf(1) }
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
                TopBar(navController, user, isFavorite) {
                    toggleFavorite(viewModel, isFavorite, productId).also {
                        isFavorite = !isFavorite
                    }
                }
            } else {
                TopBar(navController, null, false) {}
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
        }
    ) { paddingValues ->
        when (uiState) {
            is ProductUiState.Error -> Failed((uiState as ProductUiState.Error).message)
            is ProductUiState.Loading -> Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

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
fun QuantitySelector(quantity: Int, onQuantityChange: (Int) -> Unit) {
    Row(
        Modifier
            .padding(4.dp)
            .background(
                Color.LightGray.copy(0.2f),
                RoundedCornerShape(25.dp)
            )
            .width(120.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Filled.Remove,
            contentDescription = "Decrease",
            Modifier
                .padding(8.dp)
                .background(White, shape = CircleShape)
                .clip(CircleShape)
                .clickable { if (quantity > 1) onQuantityChange(quantity - 1) }
                .padding(4.dp)
//                    .size(35.dp)
            ,
            tint = Color.DarkGray
        )

        Text(textAlign =  TextAlign.Center, text = quantity.toString(), fontSize = 16.sp, color = Color.Black)

        Icon(
            Icons.Filled.Add,
            contentDescription = "Decrease",
            Modifier
                .padding(8.dp)
                .background(White, shape = CircleShape)
                .clip(CircleShape)
                .clickable {
                    onQuantityChange(quantity + 1)
                }
                .padding(4.dp)
            ,
            tint = Color.DarkGray
        )

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
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page ->
                NetworkImage(
                    modifier = Modifier.fillMaxWidth(),
                    url = product.images[page],
                    contentScale = ContentScale.FillWidth
                )
            }
            DotsIndicator(
                totalDots = product.images.size,
                selectedIndex = pagerState.currentPage,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-70).dp)
                    .clip(CircleShape)
                    .background(White.copy(0.5f))
            )
        }

        // Main Content Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-50).dp)
                .background(White, shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp))
                .padding(24.dp)
        ) {
            // Title & Category
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Column(Modifier.weight(1f)) {
                    Text(
                        product.title.capitalizeEachWord(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black.copy(0.8f)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        product.category.capitalizeEachWord(),
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
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

            Spacer(Modifier.height(24.dp))

            // Color
            if (product.colors.isNotEmpty()) {
                Row {
                    VariantHeaderText("Color")
                    Spacer(Modifier.width(4.dp))
                    VariantValueText(selectedColor)
                }
                Spacer(Modifier.height(12.dp))
                LazyRow {
                    items(product.colors) { colorName ->
                        val color = parseColorFromName(colorName)
                        val isSelected = selectedColor == colorName
                        Surface(
                            modifier = Modifier
                                .size(35.dp)
                                .clickable { onColorSelected(colorName) },
                            shape = CircleShape,
                            color = color,
                            shadowElevation = 4.dp
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

            // Size
            if (product.sizes.isNotEmpty()) {
                Row {
                    VariantHeaderText("Size")
                    Spacer(Modifier.width(4.dp))
                    VariantValueText(selectedSize)
                }
                Spacer(Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(product.sizes) { size ->
                        val isSelected = selectedSize == size
                        FilterChip(
                            selected = isSelected,
                            onClick = { onSizeSelected(size) },
                            label = {
                                Text(
                                    text = size.uppercase(),
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(vertical = 6.dp)
                                )
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = Color.Transparent,
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

            Spacer(Modifier.height(24.dp))
            VariantHeaderText("Description")
            Spacer(Modifier.height(8.dp))
            Text(text = product.description, fontSize = 14.sp, color = Color.Gray)
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

@Composable
fun TopBar(
    navController: NavController,
    user: FirebaseUser?,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackButton(
            navController = navController,
            modifier = Modifier
                .clip(CircleShape)
                .background(White.copy(0.5f))
        )
        if (user != null) {
            Box(
                modifier = Modifier
                    .padding(16.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(White.copy(0.5f)),
                    onClick = onFavoriteClick
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else LocalContentColor.current
                    )
                }
            }
        }
    }
}


fun parseColorFromName(name: String): Color {
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
        else -> try {
            Color(android.graphics.Color.parseColor(name))
        } catch (e: Exception) {
            Color.LightGray
        }
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    unSelectedColor: Color = Color.Gray,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(all = 8.dp)
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(if (index == selectedIndex) Teal else unSelectedColor)
            )
            Spacer(Modifier.width(4.dp))
        }
    }
}

fun String.capitalizeEachWord(): String =
    this.lowercase()
        .split(" ")
        .joinToString(" ") { word ->
            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }

