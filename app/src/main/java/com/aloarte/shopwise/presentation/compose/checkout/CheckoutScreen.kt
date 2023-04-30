package com.aloarte.shopwise.presentation.compose.checkout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloarte.shopwise.R
import com.aloarte.shopwise.domain.ProductBo
import com.aloarte.shopwise.domain.ProductType
import com.aloarte.shopwise.presentation.UiEvent
import com.aloarte.shopwise.presentation.UiState
import com.aloarte.shopwise.presentation.compose.commons.TitleText
import com.aloarte.shopwise.presentation.getProductBackground
import com.aloarte.shopwise.presentation.getProductImage

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
        SelectedProductList(state = state)
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
fun SelectedProductList(state: UiState) {
    val cartItems = state.cart.getCartItems()
    LazyColumn {
        items(cartItems.size) { itemIndex ->
            CheckoutProductItem(
                state = state,
                item = cartItems[itemIndex]
            )

        }
    }

}

@Composable
fun CheckoutProductItem(state: UiState, item: Pair<ProductBo, Int>) {
    val product = item.first
    val amountOfProduct = item.second

    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        CheckoutProductImage(modifier = Modifier.align(Alignment.CenterStart), product.type)
        CheckoutProductTitlePrice(
            modifier = Modifier.align(Alignment.Center),
            state = state,
            product = product,
            amount = amountOfProduct
        )

    }
}

@Composable
fun CheckoutProductImage(
    modifier: Modifier = Modifier, type: ProductType
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .width(80.dp)
            .border(
                border = BorderStroke(1.5.dp, Color.Black),
                shape = RoundedCornerShape(20.dp)
            )
            .background(type.getProductBackground()),

        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp),
            painter = painterResource(id = type.getProductImage()),
            contentDescription = stringResource(id = R.string.img_desc_product)
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CheckoutProductTitlePrice(
    modifier: Modifier = Modifier,
    state: UiState,
    product: ProductBo,
    amount: Int
) {
    val productsDiscounted = state.cart.getDiscountedCountByType(amount, product.type)
    val productsPrice = state.cart.getItemsPriceByType(product.type)
    val productPriceWithoutDiscount  = state.cart.getItemsPriceWithoutDiscountByType(product.type)

    Column(modifier.padding(10.dp)) {
        Text(
            fontSize = 20.sp,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraLight,
            text = product.name
        )
        Row(){
            Text(
                fontSize = 20.sp,
                color = Color.LightGray,
                style = if (productsDiscounted > 0) {
                    MaterialTheme.typography.headlineSmall.copy(textDecoration = TextDecoration.LineThrough)
                } else {
                    MaterialTheme.typography.headlineSmall
                },
                fontWeight = FontWeight.ExtraLight,
                text = productPriceWithoutDiscount.toString()
            )
            if (productsDiscounted > 0) {
                Text(
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraLight,
                    text = productsPrice.toString()
                )
            }
        }

    }

}

@Composable
fun TotalAndPaymentRow() {

}
