package com.aloarte.shopwise.presentation.compose.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aloarte.shopwise.domain.ProductBo
import com.aloarte.shopwise.domain.ProductType
import com.aloarte.shopwise.presentation.UiEvent
import com.aloarte.shopwise.presentation.UiState
import java.util.Locale

@Composable
fun ListScreen(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    var filterType by remember { mutableStateOf(ProductType.Unknown) }
    val products = state.productList.filter {
        shouldFilter(it, searchText, filterType)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 15.dp, horizontal = 15.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        IconsRow(state.cartSize) {
            onEventTriggered.invoke(UiEvent.GoCart)

        }
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
        GridContent(
            enableCheckout = state.cartSize>0,
            products = products,
            onAddToCart = { product, quantity ->
                onEventTriggered.invoke(UiEvent.AddProduct(product, quantity))
            },
            onItemClicked = { onEventTriggered .invoke(UiEvent.OpenDetail(it))},
            onGoToCheckout = { onEventTriggered.invoke(UiEvent.GoCart) })

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



