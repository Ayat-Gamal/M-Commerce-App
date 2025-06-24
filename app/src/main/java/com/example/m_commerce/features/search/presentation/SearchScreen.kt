package com.example.m_commerce.features.search.presentation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.m_commerce.config.theme.Black
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.Empty
import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.product.presentation.components.ProductCard

@Composable
fun SearchScreen(
    query: MutableState<String>,
    paddingValues: PaddingValues,
    navigateToProductDetails: (String) -> Unit,
    deleteProduct: (Product) -> Unit = {},
    viewModel: SearchViewModel = hiltViewModel()
) {
//    var showFilterDropDownMenu by remember {
//        mutableStateOf(false)
//    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAllProducts(false)
    }

    LaunchedEffect(query.value) {
        viewModel.search(query.value)
    }

    var expandedFilter by remember { mutableStateOf<String?>(null) }
    val selectedFilters = remember { mutableStateMapOf<String, String>() }

    val showFilterDropDownMenu = expandedFilter != null



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
                LabelRangeSlider()
                Spacer(Modifier.height(24.dp))
                FilterBar(
                    selectedFilters = selectedFilters,
                    expandedFilter = expandedFilter,
                    onFilterClicked = { filter ->
                        expandedFilter = if (expandedFilter == filter) null else filter
                    }
                )
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
                            selectedFilters[expandedFilter!!] = selected
//                            viewModel.filter(expandedFilter!!, selected)
                            expandedFilter = null
                        }
                    )
                }
//                FilterBar {
//                    showFilterDropDownMenu = !showFilterDropDownMenu
//                }

//                AnimatedVisibility(
//                    visible = showFilterDropDownMenu,
//                    enter = fadeIn() + expandVertically(),
//                    exit = fadeOut() + shrinkVertically()
//                ) {
//                    FilterDropMenu()
//                }
                Text(
                    "Showing Results for ${query.value}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(16.dp))
            }
        }

        when (uiState) {
            is SearchUiState.Success -> {
                val data = (uiState as SearchUiState.Success).products
                Log.d("TAG", "SearchScreen: SearchUiState.Success")

                items(data.size) {
                    ProductCard(
                        product = data[it],
                        onClick = {
                            navigateToProductDetails(data[it].id)
                        },
                        deleteFromWishList = { deleteProduct(data[it]) },
                    )
                }

            }

            is SearchUiState.Empty -> {
                Log.d("TAG", "SearchScreen: SearchUiState.Empty")
                item {
                    Empty("No result found")
                }
            }

            is SearchUiState.Error -> {
                Log.d("TAG", "SearchScreen: SearchUiState.Error")
            }

            is SearchUiState.Loading -> {
                Log.d("TAG", "SearchScreen: SearchUiState.Loading")
            }
        }
    }


}

@Composable
fun FilterDropMenu() {
    val list = listOf("Red", "Blue", "Yellow", "Yellow", "Yellow")
    val categoryName = "Color"
    Column {
        Text(
            categoryName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        FlowRow {
            list.forEach {
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = Black
                    ),
                    border = BorderStroke(1.dp, Color.LightGray),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = it)
                }
            }
        }

        CustomDivider()
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
fun LabelRangeSlider() {
    val minValue = 0f
    val maxValue = 100f
    var priceRange by remember { mutableStateOf(minValue..maxValue) }
    val labelSteps = (minValue.toInt()..maxValue.toInt() step 10).toList()

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
            priceRange = it
        },
        valueRange = minValue..maxValue,
        colors = SliderDefaults.colors(
            thumbColor = Teal,
            activeTrackColor = Teal,
            inactiveTrackColor = Color.Gray,
            activeTickColor = Color.Transparent,
            inactiveTickColor = Color.Transparent
        ),
        startThumb = { ThumbRectangularShape() },
        endThumb = { ThumbRectangularShape() },
    )

    Spacer(modifier = Modifier.height(0.dp))

    // labels
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "\$${minValue}",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Text(
            text = "\$${maxValue}",
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

//@Composable
//fun FilterBar(
//    selectedFilters: Map<String, String>,
//    onFilterClicked: (String) -> Unit
//) {
//    val filters = listOf("Category", "Brand", "Color")
//    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//        items(filters) { filter ->
//            FilterButton(
//                label = filter,
//                badgeCount = if (selectedFilters.containsKey(filter)) 1 else 0,
//                onClick = { onFilterClicked(filter) }
//            )
//        }
//    }
//}

/*@Composable
fun FilterButton(label: String, badgeCount: Int = 0, onClick: () -> Unit) {
    Box {
        Button(onClick = onClick, shape = RoundedCornerShape(12.dp)) {
            Text(text = label)
        }

        if (badgeCount > 0) {
            Box(
                modifier = Modifier
                    .offset(x = 40.dp, y = (-6).dp)
                    .size(16.dp)
                    .background(Color.Black, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("1", style = MaterialTheme.typography.labelSmall.copy(color = Color.White))
            }
        }
    }
}*/



//@Composable
//fun FilterBar(showMenu: () -> Unit) {
//    LazyRow(
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
//    ) {
//        item {
//            FilterButton(label = "Category") {
//                // Expand Gender filter
//            }
//        }
//
//        item {
//            FilterButton(label = "Brand") {
//                // Expand Gender filter
//            }
//        }
//
//        item {
//            FilterButton(label = "Color") {
//                showMenu()
//                // Expand Gender filter
//            }
//        }
//
//    }
//}


/*@Composable
fun FilterButton(
    label: String,
    onClick: () -> Unit
) {
    var flag by remember { mutableStateOf(true) }
    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        color = if (flag) Color.Unspecified else Teal.copy(0.2f),
        modifier = Modifier
            .clickable {
                flag = !flag
                onClick()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.Transparent)
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = label,
                color = Color.Black,
                fontSize = 18.sp
            )
            Icon(
                imageVector = if (flag) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = "Dropdown",
                tint = Color.Gray
            )
        }
    }
}*/


//@Preview(showSystemUi = true)
//@Composable
//private fun SearchScreenPreview() {
//    SearchScreen()
//}

@Composable
fun FilterBar(
    selectedFilters: Map<String, String>,
    expandedFilter: String?,
    onFilterClicked: (String) -> Unit
) {
    val filters = listOf("Category", "Brand", "Color")
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(filters) { filter ->
            FilterButton(
                label = filter,
                isSelected = expandedFilter == filter, // <- highlight when expanded
                badgeCount = if (selectedFilters.containsKey(filter)) 1 else 0,
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
            )
        ) {
            Text(text = label)
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
                    .offset(x = 40.dp, y = (-6).dp)
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
    selectedFilters: Map<String, String>,
    onItemSelected: (String) -> Unit
) {
    val options = when (filterType) {
        "Color" -> listOf("Red", "Blue", "Yellow")
        "Category" -> listOf("Shoes", "Shirts", "Pants")
        "Brand" -> listOf("Nike", "Adidas", "Puma")
        else -> emptyList()
    }

    val selected = selectedFilters[filterType]

    Column {
        Text(
            filterType,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        FlowRow(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            options.forEach { option ->
                val isSelected = selected == option

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




