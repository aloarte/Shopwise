package com.aloarte.shopwise.presentation.compose.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloarte.shopwise.R
import com.aloarte.shopwise.domain.ProductBo
import com.aloarte.shopwise.domain.ProductType
import com.aloarte.shopwise.presentation.UiEvent
import com.aloarte.shopwise.presentation.UiState
import com.aloarte.shopwise.presentation.compose.list.CheckoutRow
import com.aloarte.shopwise.presentation.getProductBackground
import com.aloarte.shopwise.presentation.getProductImage

@Composable
fun DetailScreen(productType: String?, state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    val product = state.productList.find { it.code == productType }
    product?.let {
        Column {
            DetailImage(it.type)
            DetailTitle(it)
            it.description?.let { description -> DetailDescription(description) }
            DetailAddToCart(onEventTriggered)
        }
    }
}

@Composable
fun DetailImage(type: ProductType) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(type.getProductBackground()),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = type.getProductImage()),
            modifier = Modifier
                .height(100.dp)
                .width(100.dp),
            colorFilter = ColorFilter.tint(Color.Black),
            contentDescription = stringResource(id = R.string.img_desc_detail_image)
        )
    }
}

@Composable
fun DetailTitle(product: ProductBo) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp,vertical = 20.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraLight,
            text = product.name
        )
        Text(
            modifier = Modifier.align(Alignment.CenterEnd),
            fontSize = 30.sp,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraLight,
            text = "${product.price} €"
        )
    }
}

@Composable
fun DetailDescription(description: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 20.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            fontSize = 20.sp,
            color = Color.LightGray,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraLight,
            text = description
        )
    }
}

@Composable
fun DetailAddToCart(onEventTriggered: (UiEvent) -> Unit) {
    CheckoutRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp,vertical = 20.dp),
        buttonText = R.string.detail_add_cart_btn,
    ) {
        onEventTriggered.invoke(UiEvent.GoCheckout)
    }
}