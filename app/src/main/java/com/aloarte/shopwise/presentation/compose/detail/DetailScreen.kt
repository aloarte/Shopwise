package com.aloarte.shopwise.presentation.compose.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aloarte.shopwise.R
import com.aloarte.shopwise.domain.ProductType
import com.aloarte.shopwise.presentation.UiEvent
import com.aloarte.shopwise.presentation.UiState
import com.aloarte.shopwise.presentation.getProductBackground
import com.aloarte.shopwise.presentation.getProductImage

@Composable
fun DetailScreen(productType: String?, state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    val product = state.productList.find { it.code == productType }
    product?.let{
        Column() {
            DetailImage(product.type)
            DetailTitle()
            DetailDescription()
            DetailAddToCart()
        }
    }


}

@Composable
fun DetailAddToCart() {
}

@Composable
fun DetailDescription() {


}

@Composable
fun DetailTitle() {
}

@Composable
fun DetailImage(type: ProductType) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(type.getProductBackground()),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = type.getProductImage()),
            modifier = Modifier
                .height(80.dp)
                .width(80.dp),
            colorFilter = ColorFilter.tint(Color.Black),
            contentDescription = stringResource(id = R.string.img_desc_detail_image)
        )
    }


}
