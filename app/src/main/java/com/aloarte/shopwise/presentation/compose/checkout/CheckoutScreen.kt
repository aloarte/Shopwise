package com.aloarte.shopwise.presentation.compose.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aloarte.shopwise.R
import com.aloarte.shopwise.presentation.UiEvent
import com.aloarte.shopwise.presentation.UiState
import com.aloarte.shopwise.presentation.compose.commons.TitleText

@Composable
fun CheckoutScreen(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleRow(onEventTriggered)
        SelectedProductList(state = state, onEventTriggered = onEventTriggered)
        TotalAndPaymentRow()
    }
}

@Composable
fun TitleRow(onEventTriggered: (UiEvent) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            modifier = Modifier
                .padding(10.dp)
                .height(30.dp)
                .width(30.dp)
                .align(Alignment.CenterStart)
                .clickable(onClick = { onEventTriggered.invoke(UiEvent.GoList) }),
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.img_desc_exit_checkout_icon)
        )

        TitleText(
            title = stringResource(id = R.string.list_products_title),
            modifier = Modifier.align(Alignment.Center)
        )

    }

}

@Composable
fun SelectedProductList(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    val cartItems = state.cart.getCartItems()
    LazyColumn {
        items(cartItems.size) { itemIndex ->
            val product = cartItems[itemIndex]
            CheckoutProductItem(
                state = state,
                item = product
            ) { quantity ->
                onEventTriggered.invoke(UiEvent.ReplaceProductQuantity(product.first, quantity))
            }
        }
    }
}

@Composable
fun TotalAndPaymentRow() {

}
