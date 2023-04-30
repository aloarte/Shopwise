package com.aloarte.shopwise.presentation.compose.checkout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloarte.shopwise.R
import com.aloarte.shopwise.domain.ProductBoComparator
import com.aloarte.shopwise.domain.ProductType
import com.aloarte.shopwise.presentation.UiEvent
import com.aloarte.shopwise.presentation.UiState
import com.aloarte.shopwise.presentation.compose.commons.PriceRow
import com.aloarte.shopwise.presentation.compose.commons.TitleText
import com.aloarte.shopwise.presentation.compose.enums.PriceRowType

@Composable
fun CheckoutScreen(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleRow(onEventTriggered)
            SelectedProductList(state = state, onEventTriggered = onEventTriggered)

        }
        TotalAndPaymentRow(
            modifier = Modifier.align(Alignment.BottomCenter),
            state = state,
            onEventTriggered = onEventTriggered
        )
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
    val cartItems = state.cart.getCartItems().sortedWith(ProductBoComparator)
    LazyColumn {
        items(cartItems.size) { itemIndex ->
            val product = cartItems[itemIndex]
            Spacer(modifier = Modifier.height(10.dp))
            CheckoutProductItem(
                state = state,
                item = product
            ) { quantity ->
                if (quantity > 0) {
                    onEventTriggered.invoke(UiEvent.ReplaceProductQuantity(product.first, quantity))
                } else {
                    onEventTriggered.invoke(UiEvent.RemoveProduct(product.first))
                }
            }
        }
    }
}

@Composable
fun TotalAndPaymentRow(
    modifier: Modifier = Modifier,
    state: UiState,
    onEventTriggered: (UiEvent) -> Unit
) {
    val vouchersPrice = state.cart.getItemsPriceByType(ProductType.Voucher)
    val tshirtsPrice = state.cart.getItemsPriceByType(ProductType.Tshirt)
    val mugsPrice = state.cart.getItemsPriceByType(ProductType.Mug)
    val totalWithoutDiscount = state.cart.checkoutWithoutDiscount()
    val totalPrice = state.cart.checkout()

    Column(modifier = modifier.fillMaxWidth().padding(vertical = 20.dp)) {
        if (vouchersPrice > 0) PriceRow(
            label = stringResource(id = R.string.checkout_label_total_vouchers),
            price = vouchersPrice
        )
        if (tshirtsPrice > 0) PriceRow(
            label = stringResource(id = R.string.checkout_label_total_tshirts),
            price = tshirtsPrice
        )
        if (mugsPrice > 0) PriceRow(
            label = stringResource(id = R.string.checkout_label_total_mugs),
            price = mugsPrice
        )
        PriceRow(
            label = stringResource(id = R.string.checkout_label_total),
            price = totalPrice,
            type = PriceRowType.Total
        )
        PriceRow(
            label = stringResource(id = R.string.checkout_label_total_discount),
            price = totalWithoutDiscount - totalPrice,
            type = PriceRowType.Discount
        )

        Spacer(Modifier.height(20.dp))
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = Color.LightGray,
                containerColor = Color.Transparent
            ),
            border = BorderStroke(
                1.5.dp,
                MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(10.dp),
            onClick = { onEventTriggered.invoke(UiEvent.GoPayment(totalPrice)) }
        ) {

            Text(
                color = Color.Black,
                fontSize = 14.sp,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraLight,
                text = stringResource(id = R.string.checkout_add_cart_btn)
            )
        }
    }

}
