@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.aloarte.shopwise.presentation.compose.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aloarte.shopwise.R
import com.aloarte.shopwise.domain.ProductBo
import com.aloarte.shopwise.domain.ProductType
import com.aloarte.shopwise.domain.ProductType.Companion.fromString
import com.aloarte.shopwise.presentation.compose.TitleText
import com.aloarte.shopwise.presentation.compose.navigation.Screen
import com.aloarte.shopwise.presentation.ui.theme.MugBackground
import com.aloarte.shopwise.presentation.ui.theme.TshirtBackground
import com.aloarte.shopwise.presentation.ui.theme.VoucherBackground
import java.util.Locale

@Composable
fun ListScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var searchText by remember { mutableStateOf("") }
    var filterType by remember { mutableStateOf(ProductType.Unknown) }

    val products = listOf(
        ProductBo(
            type = ProductType.Voucher,
            code = "VOUCHER",
            name = "Cabify Voucher",
            price = 5.0
        ),
        ProductBo(
            type = ProductType.Tshirt,
            code = "TSHIRT",
            name = "Cabify T-shirt",
            price = 20.0
        ),
        ProductBo(
            type = ProductType.Mug,
            code = "MUG",
            name = "Cabify Coffee Mug",
            price = 7.5
        )
    ).filter {
        shouldFilter(it, searchText, filterType)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 15.dp)
            .scrollable(state = scrollState, orientation = Orientation.Vertical)
    ) {
        IconsRow()
        Spacer(modifier = Modifier.height(10.dp))
        SearchRow {
            searchText = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        FilterRow {
            filterType = it
            searchText = ""
        }
        Spacer(modifier = Modifier.height(10.dp))
        GridContent(products)
        Spacer(modifier = Modifier.height(10.dp))
        CheckoutRow(navController)
    }
}

fun shouldFilter(product: ProductBo, searchText: String, filterType: ProductType): Boolean {
    val searchTextFilter = if (filterType != ProductType.Unknown) {
        product.type == filterType
    } else if (searchText.isEmpty()) {
        true
    } else {
        searchText.isNotEmpty() && areSameStringsInLower(product.code, searchText)
    }
    return searchTextFilter
}

fun areSameStringsInLower(code: String, searchText: String) = with(Locale.getDefault()) {
    code.lowercase(this).startsWith(searchText.lowercase(this))
}


@Composable
fun IconsRow() {
}

@Composable
fun SearchRow(onContentSearched: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
    ) {
        OutlinedTextField(

            value = searchText,
            onValueChange = {
                searchText = it
                onContentSearched.invoke(searchText)
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    onContentSearched.invoke(searchText)
                }),

            placeholder = { Text(text = "Search for any product name") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon"
                )
            }
        )

    }
}


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
                    name = if (filterList[it] == ProductType.Unknown) "No filter" else filterList[it].name,
                    isSelected = selectedFilter == filterList[it],
                    onSelectionChanged = { selectionValue ->
                        onFilterChanged(fromString(selectionValue))
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Chip(
    name: String = "Chip",
    isSelected: Boolean = false,
    onSelectionChanged: (String) -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .height(40.dp)
            .width(75.dp)
            .padding(4.dp),
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
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
                text = name,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun GridContent(products: List<ProductBo>) {
    TitleText("PRODUCTS")

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        items(products.size) {
            ProductItem(products[it])
        }
    }
}

@Composable
fun ProductItem(product: ProductBo) {

    var liked by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            onClick = { },
            modifier = Modifier
                .height(200.dp)
                .width(150.dp),
            colors = CardDefaults.cardColors(containerColor = getProductBackground(product.type))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp),
                    painter = painterResource(id = getProductImage(product.type)),
                    contentDescription = "Product image"
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Column(Modifier.align(Alignment.CenterStart)) {
                Text(
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraLight,
                    text = product.name
                )
                Text(
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraLight,
                    text = "${product.price} €"
                )

            }
            Column(
                Modifier
                    .border(
                        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .align(Alignment.CenterEnd)
                    .height(35.dp)
                    .width(35.dp)
                    .padding(5.25.dp)
                    .clickable { liked = !liked },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    colorFilter = ColorFilter.tint(if (liked) Color.Red else Color.Black),
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp),
                    painter = painterResource(id = if (liked) R.drawable.ic_like_filled else R.drawable.ic_like),
                    contentDescription = "Like icon"
                )
            }

        }
    }

}


fun getProductImage(productType: ProductType) = when (productType) {
    ProductType.Voucher -> R.drawable.ic_voucher
    ProductType.Tshirt -> R.drawable.ic_tshirt
    ProductType.Mug -> R.drawable.ic_mug
    else -> R.drawable.ic_voucher
}

fun getProductBackground(productType: ProductType) = when (productType) {
    ProductType.Voucher -> VoucherBackground
    ProductType.Tshirt -> TshirtBackground
    ProductType.Mug -> MugBackground
    else -> MugBackground

}

@Composable
fun CheckoutRow(navController: NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate(Screen.PaymentScreen.route) }) {
            Text(text = "Checkout")
        }
    }
}



