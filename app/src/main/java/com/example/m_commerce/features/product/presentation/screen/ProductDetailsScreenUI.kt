package com.example.m_commerce.features.product.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.m_commerce.config.theme.DarkestGray
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.product.presentation.ProductUiState
import com.example.m_commerce.features.product.presentation.ProductViewModel
import com.example.m_commerce.features.product.presentation.components.VariantHeaderText
import com.example.m_commerce.features.product.presentation.components.VariantValueText


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun ProductDetailsScreenUI(
    modifier: Modifier = Modifier,
    productId: String,
    navController: NavHostController,
    viewModel: ProductViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getProductById(productId)
    }

    val isLoading = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold {
        when (uiState) {
            is ProductUiState.Success -> {
                val product = (uiState as ProductUiState.Success).product
                val pagerState = rememberPagerState(pageCount = { product.images.size })
                var selectedSize by remember { mutableStateOf(product.sizes[0]) }
                var selectedColor by remember { mutableStateOf(product.colors[0]) }
                var favoriteState by remember { mutableStateOf(if ((uiState as ProductUiState.Success).isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder) }
                var isFavorite by remember { mutableStateOf((uiState as ProductUiState.Success).isFavorite) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(scrollState)
                    ) {

                        DefaultTopBar(title = "", navController = navController)

                        // images
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        ) { page ->
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(product.images[page])
                                    .crossfade(true)
                                    .crossfade(500)
                                    .build(),
                                contentDescription = "Image $page",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        // indicators and wishlist icon
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            DotsIndicator(
                                totalDots = product.images.size,
                                selectedIndex = pagerState.currentPage,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                            Icon(
                                imageVector = favoriteState,
                                contentDescription = "Favorite icon",
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) {
                                        if (isFavorite) {
                                            viewModel.deleteProductFromWishlist(product.id)
                                            favoriteState = Icons.Default.FavoriteBorder
                                            isFavorite = false
                                        } else {
                                            viewModel.addProductToWishlist(product.id)
                                            favoriteState = Icons.Default.Favorite
                                            isFavorite = true
                                        }
                                    },
                                tint = if (favoriteState == Icons.Default.Favorite) {
                                    Color.Red
                                } else {
                                    LocalContentColor.current
                                }
                            )
                        }

                        // Divider
                        Spacer(Modifier.height(16.dp))
                        HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 2.dp)
                        Spacer(Modifier.height(16.dp))

                        // category and title
                        Text(
                            product.category,
                            modifier = Modifier.padding(start = 16.dp),
                            fontSize = 18.sp,
                            color = Color.LightGray,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = product.title,
                            modifier = Modifier.padding(start = 16.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(24.dp))

                        // Color
                        if (product.colors.isNotEmpty()) {
                            Row {
                                VariantHeaderText("Color")
                                VariantValueText(selectedColor)
                            }
                            Spacer(Modifier.height(8.dp))
                            LazyRow(
                                modifier = Modifier.padding(horizontal = 16.dp),
                            ) {
                                items(product.colors) { colorName ->
                                    val color = parseColorFromName(colorName)
                                    val isSelected = selectedColor == colorName

                                    Box(
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(CircleShape)
                                            .border(
                                                width = if (isSelected) 1.dp else 0.dp,
                                                color = if (isSelected) Teal else Color.Transparent,
                                                shape = CircleShape
                                            )
                                            .background(Color.Transparent)
                                            .padding(6.dp)
                                            .clip(CircleShape)
                                            .background(color)
                                            .border(
                                                width = 1.dp,
                                                color = Color.LightGray,
                                                shape = CircleShape
                                            )
                                            .clickable { selectedColor = colorName }
                                    )
                                }
                            }
                            Spacer(Modifier.height(16.dp))
                        }

                        // Size
                        if (product.sizes.isNotEmpty()) {
                            Row {
                                VariantHeaderText("Size")
                                VariantValueText(selectedSize)
                            }
                            Spacer(Modifier.height(8.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                product.sizes.forEach { size ->
                                    val isSelected = selectedSize == size
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { selectedSize = size },
                                        label = {
                                            Text(
                                                text = size.uppercase(),
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Normal,
                                                modifier = Modifier.padding(vertical = 12.dp)
                                            )
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        colors = FilterChipDefaults.filterChipColors(
                                            containerColor = Color.Transparent,
                                            selectedContainerColor = Teal,
                                            labelColor = Color.DarkGray,
                                            selectedLabelColor = Color.White
                                        ),
                                        border = FilterChipDefaults.filterChipBorder(
                                            enabled = true,
                                            selected = isSelected,
                                            borderColor = Color.LightGray,
                                            borderWidth = 1.dp,
                                            selectedBorderColor = Color.Transparent
                                        ),
                                        elevation = null
                                    )
                                }
                            }
                            Spacer(Modifier.height(16.dp))
                        }

                        // Divider
                        HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 2.dp)
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "Product Details",
                            modifier = Modifier.padding(start = 16.dp),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(16.dp))
                        HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 2.dp)

                        // Description
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "Description",
                            modifier = Modifier.padding(start = 16.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = product.description,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontSize = 16.sp,
                            color = DarkestGray
                        )
                        Spacer(Modifier.height(16.dp))

                    }

                    HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 2.dp)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 20.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Text("Total Price", color = Color.Gray)
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "${product.currencyCode} ${product.price}",
                                color = Color.DarkGray,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp
                            )
                        }

                        CustomButton(
                            onClick = {
                                isLoading.value = true
                                val variantId = viewModel.findSelectedVariantId(
                                    product.variants,
                                    selectedSize,
                                    selectedColor
                                )
                                viewModel.addToCart(variantId!!)
                            },
                            text = "Add to Cart",
                            modifier = Modifier.weight(1f),
                            isLoading = isLoading.value
                        )
                    }
                }
            }

            is ProductUiState.Error -> {}
            ProductUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }

}

fun parseColorFromName(name: String): Color {
    return when (name.lowercase()) {
        "black" -> Color.Black
        "white" -> Color.White
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
        modifier = modifier.fillMaxWidth(),
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


