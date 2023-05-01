package com.aloarte.shopwise.presentation.compose.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloarte.shopwise.R
import com.aloarte.shopwise.domain.enums.ProductType

@Composable
fun FilterRow(onFilterChanged: (ProductType) -> Unit) {
    var selectedFilter by remember { mutableStateOf(ProductType.Unknown) }
    ChipGroup(
        selectedFilter = selectedFilter,
        onFilterChanged = {
            selectedFilter = it
            onFilterChanged.invoke(selectedFilter)
        }
    )
}

@Composable
fun ChipGroup(
    selectedFilter: ProductType? = null,
    onFilterChanged: (ProductType) -> Unit = {},
) {
    val filterList =
        listOf(ProductType.Unknown, ProductType.Voucher, ProductType.Tshirt, ProductType.Mug)
    Column(modifier = Modifier.padding(8.dp)) {
        LazyRow {
            items(filterList.size) {
                Chip(
                    name = if (filterList[it] == ProductType.Unknown) stringResource(id = R.string.list_chip_all) else filterList[it].name,
                    isSelected = selectedFilter == filterList[it],
                    onSelectionChanged = { selectionValue ->
                        onFilterChanged(ProductType.fromString(selectionValue))
                    }
                )
            }
        }
    }
}

@Composable
fun Chip(
    name: String,
    isSelected: Boolean,
    onSelectionChanged: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .height(40.dp)
            .width(75.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged(name)
                }
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                color = Color.Black,
                fontSize = 14.sp,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraLight,
                text = name
            )
        }
    }
}
