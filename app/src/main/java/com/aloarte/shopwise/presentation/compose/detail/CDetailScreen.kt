package com.aloarte.shopwise.presentation.compose.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloarte.shopwise.R
import com.aloarte.shopwise.domain.model.ProductBo
import com.aloarte.shopwise.domain.enums.ProductType
import com.aloarte.shopwise.presentation.UiConstants.MAX_ITEMS_TO_ADD
import com.aloarte.shopwise.presentation.UiEvent
import com.aloarte.shopwise.presentation.UiState
import com.aloarte.shopwise.presentation.compose.enums.ModifyType
import com.aloarte.shopwise.presentation.compose.commons.ModifyQuantityIcon
import com.aloarte.shopwise.presentation.getProductBackground
import com.aloarte.shopwise.presentation.getProductImage

@Composable
fun DetailScreen(productId: String?, state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    val product = state.productList.find { it.id == productId }

    product?.let {
        Column {
            DetailImage(it) {
                onEventTriggered.invoke(UiEvent.GoList)
            }
            DetailTitle(it)
            it.description?.let { description -> DetailDescription(description) }
            DetailAddToCart { quantity ->
                onEventTriggered.invoke(UiEvent.AddProduct(product, quantity))
                onEventTriggered.invoke(UiEvent.GoList)
            }
        }
    }
}

@Composable
fun DetailImage(product: ProductBo, onIconClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(product.type.getProductBackground()),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
                .height(30.dp)
                .width(30.dp)
                .clickable(onClick = onIconClicked),
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.img_desc_exit_detail_icon)
        )

        Image(
            painter = painterResource(id = product.imageResource/*type.getProductImage()*/),
            modifier = Modifier
                .align(Alignment.Center)
                .height(200.dp)
                .width(200.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primaryContainer),
            contentDescription = stringResource(id = R.string.img_desc_detail_image)
        )
    }
}

@Composable
fun DetailTitle(product: ProductBo) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 20.dp)
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
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraLight,
            text = description
        )
    }
}

@Composable
fun DetailAddToCart(onItemsAdded: (Int) -> Unit) {
    var quantity by remember { mutableStateOf(0) }
    var enableAddProducts by remember { mutableStateOf(false) }
    var enableAdd by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        ModifyQuantityIcon(enabled = enableAddProducts, type = ModifyType.Remove) {
            //Disable the button if the quantity came from 1 to 0
            if (quantity == 1) {
                enableAddProducts = false
                quantity = 0
            } else if (quantity > 0) {
                if (quantity <= MAX_ITEMS_TO_ADD) enableAdd = true
                quantity -= 1
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier.width(20.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            fontSize = 20.sp,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraLight,
            text = quantity.toString()
        )
        Spacer(modifier = Modifier.width(10.dp))
        ModifyQuantityIcon(enabled = enableAdd, type = ModifyType.Add) {
            quantity += 1
            if (quantity == MAX_ITEMS_TO_ADD) enableAdd = false

            enableAddProducts = true
        }
        Spacer(modifier = Modifier.width(30.dp))
        OutlinedButton(
            enabled = enableAddProducts,
            modifier = Modifier
                .width(150.dp),
            colors = ButtonDefaults.buttonColors(disabledContainerColor =MaterialTheme.colorScheme.onPrimary, containerColor = Color.Transparent),
            border = BorderStroke(1.5.dp, if (enableAddProducts) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary),
            shape = RoundedCornerShape(10.dp),
            onClick = { onItemsAdded.invoke(quantity) }
        ) {

            Text(
                color = MaterialTheme.colorScheme.primaryContainer,
                fontSize = 14.sp,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraLight,
                text = stringResource(id = R.string.detail_add_cart_btn)
            )
        }
    }

}