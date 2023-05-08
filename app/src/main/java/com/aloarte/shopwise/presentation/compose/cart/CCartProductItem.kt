package com.aloarte.shopwise.presentation.compose.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloarte.shopwise.R
import com.aloarte.shopwise.domain.cart.ProductBoComparator
import com.aloarte.shopwise.domain.model.ProductBo
import com.aloarte.shopwise.presentation.UiConstants
import com.aloarte.shopwise.presentation.UiEvent
import com.aloarte.shopwise.presentation.UiState
import com.aloarte.shopwise.presentation.compose.commons.ModifyQuantityIcon
import com.aloarte.shopwise.presentation.compose.enums.ModifyType
import com.aloarte.shopwise.presentation.compose.enums.QuantityIconSizeType
import com.aloarte.shopwise.presentation.getProductBackground

@Composable
fun SelectedProductList(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    val cartItems = state.cart.getCartItems().sortedWith(ProductBoComparator)
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp),
    ) {
        items(cartItems.size) { itemIndex ->
            val product = cartItems[itemIndex]
            Spacer(modifier = Modifier.height(10.dp))
            CartProductItem(
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
fun CartProductItem(state: UiState, item: Pair<ProductBo, Int>, onItemsAdded: (Int) -> Unit) {
    val product = item.first
    val productQuantity = item.second
    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        CartProductImage(modifier = Modifier.align(Alignment.CenterStart), product)
        CartProductTitlePrice(
            modifier = Modifier.align(Alignment.Center),
            state = state,
            product = product,
            quantity = productQuantity
        )
        CartChangeProductQuantity(
            modifier = Modifier.align(Alignment.CenterEnd),
            quantity = productQuantity,
            onItemsAdded = onItemsAdded
        )

    }
}

@Composable
fun CartProductImage(
    modifier: Modifier = Modifier, product: ProductBo
) {
    Card(
        modifier = Modifier
            .height(80.dp)
            .width(80.dp)
            .clickable(onClick = {}, enabled = false),
        colors = CardDefaults.cardColors(containerColor = product.type.getProductBackground())
    ) {
        Box(
            modifier = modifier
                .height(80.dp)
                .width(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp),
                painter = painterResource(id = product.imageResource/* type.getProductImage()*/),
                contentDescription = stringResource(id = R.string.img_desc_product)
            )
        }
    }

}

@Composable
fun CartProductTitlePrice(
    modifier: Modifier = Modifier,
    state: UiState,
    product: ProductBo,
    quantity: Int
) {
    val productsDiscounted = state.cart.areProductsDiscounted(quantity, product.type)
    val productsPrice = state.cart.getDiscountedItemsPrice(product.type,product.name)
    val productPriceWithoutDiscount = state.cart.getNotDiscountedItemsPrice(product.type,product.name)

    Row(
        modifier
            .padding(vertical = 10.dp, horizontal = 40.dp)
            .width(150.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                fontSize = 20.sp,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraLight,
                text = product.name
            )
            Row {
                Text(
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = if (productsDiscounted) {
                        MaterialTheme.typography.labelSmall.copy(textDecoration = TextDecoration.LineThrough)
                    } else {
                        MaterialTheme.typography.labelSmall
                    },
                    fontWeight = FontWeight.ExtraLight,

                    text = "$productPriceWithoutDiscount €"
                )
                if (productsDiscounted) {
                    Spacer(Modifier.width(10.dp))
                    Text(
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraLight,
                        text = "$productsPrice €"
                    )
                }
            }

        }
    }

}

@Composable
fun CartChangeProductQuantity(
    modifier: Modifier,
    quantity: Int,
    onItemsAdded: (Int) -> Unit
) {
    var productQuantity by remember { mutableStateOf(quantity) }
    var enableAddProducts by remember { mutableStateOf(productQuantity > 0) }
    var enableAdd by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        ModifyQuantityIcon(
            size = QuantityIconSizeType.Small,
            enabled = enableAdd,
            type = ModifyType.Add
        ) {
            productQuantity += 1
            if (productQuantity == UiConstants.MAX_ITEMS_TO_ADD) enableAdd = false
            enableAddProducts = true
            onItemsAdded.invoke(productQuantity)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier.width(20.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            fontSize = 20.sp,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraLight,
            text = productQuantity.toString()
        )
        Spacer(modifier = Modifier.width(10.dp))
        ModifyQuantityIcon(
            size = QuantityIconSizeType.Small,
            enabled = enableAddProducts,
            type = ModifyType.Remove
        ) {
            //Disable the button if the quantity came from 1 to 0
            if (productQuantity == 1) {
                enableAddProducts = false
                productQuantity = 0
            } else if (productQuantity > 0) {
                if (productQuantity <= UiConstants.MAX_ITEMS_TO_ADD) enableAdd = true
                productQuantity -= 1
            }
            onItemsAdded.invoke(productQuantity)
        }

    }
}

