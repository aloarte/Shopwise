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
            type = ProductType.Mug,
            code = "MUG",
            name = "Cabify Coffee Mug",
            price = 7.5
        ),
        ProductBo(
            type = ProductType.Mug,
            code = "MUG",
            name = "Cabify Coffee Mug",
            price = 7.5
        ),
        ProductBo(
            type = ProductType.Tshirt,
            code = "TSHIRT",
            name = "Cabify T-shirt",
            price = 20.0
        ),
        ProductBo(
            type = ProductType.Voucher,
            code = "VOUCHER",
            name = "Cabify Voucher",
            price = 5.0
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



