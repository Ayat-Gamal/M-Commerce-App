package com.example.m_commerce.features.search.presentation

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.config.theme.Black
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.features.brand.presentation.screen.products
import com.example.m_commerce.features.product.presentation.components.ProductsGridView

@Composable
fun SearchScreen() {
    val query = "t-shirt"
    var showFilterDropDownMenu by remember { mutableStateOf(false) }
    Column(Modifier.padding(16.dp)) {
        LabelRangeSlider()
        Spacer(Modifier.height(24.dp))
        FilterBar {
            showFilterDropDownMenu = !showFilterDropDownMenu
        }
        CustomDivider()
        AnimatedVisibility(
            visible = showFilterDropDownMenu,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            FilterDropMenu()
        }
        Text("Showing Results for $query", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        ProductsGridView(
            modifier = Modifier,
            products = products,
            deleteFromWishList = null,
            navigateToProduct = {}
        )
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

@Composable
fun FilterBar(showMenu: () -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            FilterButton(label = "Category") {
                // Expand Gender filter
            }
        }

        item {
            FilterButton(label = "Brand") {
                // Expand Gender filter
            }
        }

        item {
            FilterButton(label = "Color") {
                showMenu()
                // Expand Gender filter
            }
        }

    }
}


@Composable
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
}


//@Preview(showSystemUi = true)
//@Composable
//private fun SearchScreenPreview() {
//    SearchScreen()
//}