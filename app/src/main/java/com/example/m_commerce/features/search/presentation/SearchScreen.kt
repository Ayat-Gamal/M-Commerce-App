package com.example.m_commerce.features.search.presentation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.config.theme.Black
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.Empty
import com.example.m_commerce.core.shared.components.Failed
import com.example.m_commerce.core.shared.components.SearchBarWithClear
import com.example.m_commerce.core.shared.components.default_top_bar.BackButton
import com.example.m_commerce.features.product.presentation.components.ProductCard
import kotlin.math.exp

@Composable
fun SearchScreen(
    navController: NavHostController,
    isWishlist: Boolean,
    viewModel: SearchViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAllProducts(isWishlist)
    }

//    LaunchedEffect(query.value) {
//        viewModel.search(query.value)
//    }

    var expandedFilter by remember { mutableStateOf<String?>(null) }
    val selectedFilters = remember { mutableStateMapOf<String, List<String>>() }
    var selectedRange by remember { mutableStateOf(0f..300f) }
    val showFilterDropDownMenu = expandedFilter != null

    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                BackButton(navController)
                SearchBarWithClear(
                    query = query,
                    onQueryChange = {
                        query = it
                        viewModel.searchAndFilter(it, selectedFilters, selectedRange)
                    },
                    onClear = {
                        query = ""
                        viewModel.clear()
                    },
                    enabled = true,
                    placeholder = "Search in wishlist...",
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }
    ) { paddingValues ->

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {

            // header
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column {
                    if (uiState is SearchUiState.Success || uiState is SearchUiState.Empty) {
                        LabelRangeSlider(selectedRange) {
                            selectedRange = it
                            viewModel.searchAndFilter(query, selectedFilters, it)
                        }
                        Spacer(Modifier.height(24.dp))
                    }
                    FilterBar(
                        selectedFilters = selectedFilters,
                        expandedFilter = expandedFilter,
                        onFilterClicked = { filter ->
                            expandedFilter = if (expandedFilter == filter) null else filter
                        },
                    )
                    Spacer(Modifier.height(16.dp))

                    CustomDivider()
                    AnimatedVisibility(
                        visible = showFilterDropDownMenu,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        FilterDropMenu(
                            filterType = expandedFilter ?: "",
                            selectedFilters = selectedFilters,
                            onItemSelected = { selected ->
                                val currentList = selectedFilters[expandedFilter] ?: emptyList()
                                val updatedList = if (currentList.contains(selected)) {
                                    currentList - selected
//                                    selectedFilters.remove(expandedFilter)
//                                    viewModel.searchAndFilter(query, selectedFilters, selectedRange)
                                } else {
                                    currentList + selected
//                                    selectedFilters[expandedFilter!!] = selected
                                }

                                if (updatedList.isEmpty()) selectedFilters.remove(expandedFilter)
                                else selectedFilters[expandedFilter!!] = updatedList

                                    viewModel.searchAndFilter(query, selectedFilters, selectedRange)
                                // expandedFilter = null
                            },
                            viewModel = viewModel
                        )
                    }

                    Text(
                        "Showing Results for $query",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }

            when (uiState) {
                is SearchUiState.Success -> {
                    val data = (uiState as SearchUiState.Success).products
                    items(data.size) {
                        ProductCard(
                            product = data[it],
                            onClick = {
                                navController.navigate(AppRoutes.ProductDetailsScreen(data[it].id))
                            },
                            deleteFromWishList = { /*deleteProduct(data[it])*/ }, // TODO
                        )
                    }
                }

                is SearchUiState.Empty -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Empty("No result found")
                    }
                }

                is SearchUiState.Error -> {
                    val err = (uiState as SearchUiState.Error).err
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Failed(err)
                    }
                }

                is SearchUiState.Loading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        val screenHeight = LocalConfiguration.current.screenHeightDp.dp / 2
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(screenHeight),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun CustomDivider() {
    Spacer(Modifier.height(16.dp))
    HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 2.dp)
    Spacer(Modifier.height(16.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelRangeSlider(
    priceRange: ClosedFloatingPointRange<Float>,
    viewModel: SearchViewModel = hiltViewModel(),
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
) {
    val range = viewModel.priceRange.collectAsStateWithLifecycle()

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Price Range: ",
            style = MaterialTheme.typography.titleLarge,

            )
        Text(
            "\$${priceRange.start.toInt()} - \$${priceRange.endInclusive.toInt()}",
            fontSize = 18.sp,
            color = Color.Gray
        )
    }
    Spacer(Modifier.height(8.dp))

    // slider
    RangeSlider(
        value = priceRange,
        onValueChange = {
            onValueChange(it)
        },
        valueRange = range.value,
        colors = SliderDefaults.colors(
            thumbColor = Teal,
            activeTrackColor = Teal,
            inactiveTrackColor = Color.Gray,
            activeTickColor = Color.Transparent,
            inactiveTickColor = Color.Transparent
        ),
        startThumb = { ThumbRectangularShape() },
        endThumb = { ThumbRectangularShape() },
        steps = (range.value.endInclusive / 10 - 1).toInt()
    )

    // labels
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "\$${range.value.start.toInt()}",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Text(
            text = "\$${range.value.endInclusive.toInt()}",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ThumbRectangularShape() {
    Box(
        modifier = Modifier
            .size(width = 8.dp, height = 24.dp)
            .background(
                color = Teal,
                shape = RoundedCornerShape(2.dp)
            )
    )
}

@Composable
fun FilterBar(
    selectedFilters: Map<String, List<String>>,
    expandedFilter: String?,
    onFilterClicked: (String) -> Unit
) {
    val filters = listOf("Category", "Brand", "Color")
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(filters) { filter ->
            val count = selectedFilters[filter]?.size ?: 0

            FilterButton(
                label = filter,
                isSelected = expandedFilter == filter,
                badgeCount = count,
                onClick = { onFilterClicked(filter) }
            )
        }
    }
}


@Composable
fun FilterButton(
    label: String,
    isSelected: Boolean,
    badgeCount: Int = 0,
    onClick: () -> Unit
) {
    Box {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSelected) Teal else White,
                contentColor = if (isSelected) Color.White else Color.Black
            ),
            border = if (isSelected) null else BorderStroke(1.dp, Color.LightGray)
        ) {
            Text(text = label, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = if (isSelected) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }

        if (badgeCount > 0) {
            Box(
                modifier = Modifier
                    //.offset(x = 40.dp, y = (-6).dp)
                    .size(16.dp)
                    .background(Color.Black, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badgeCount.toString(),
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.White)
                )
            }
        }
    }
}


@Composable
fun FilterDropMenu(
    filterType: String,
    selectedFilters: Map<String, List<String>>,
    onItemSelected: (String) -> Unit,
    viewModel: SearchViewModel
) {
    val options = when (filterType) {
        "Color" -> viewModel.colors
        "Category" -> listOf("Shoes", "Accessories", "T-Shirts")
        "Brand" -> viewModel.brands
        else -> emptyList()
    }

    val selectedList = selectedFilters[filterType] ?: emptyList()

    Column {
        Text(
            filterType,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        FlowRow(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            options.forEach { option ->
                val isSelected = selectedList.contains(option)

                Button(
                    onClick = { onItemSelected(option) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) Teal else White,
                        contentColor = if (isSelected) Color.White else Black
                    ),
                    border = BorderStroke(1.dp, Color.LightGray),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = option)
                }
            }
        }
        CustomDivider()
    }
}




