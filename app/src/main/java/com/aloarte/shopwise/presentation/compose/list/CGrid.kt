@file:OptIn(ExperimentalMaterial3Api::class)

package com.aloarte.shopwise.presentation.compose.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloarte.shopwise.R
import com.aloarte.shopwise.domain.ProductBo
import com.aloarte.shopwise.domain.ProductType
import com.aloarte.shopwise.presentation.compose.AddProductDialog
import com.aloarte.shopwise.presentation.compose.TitleText
import com.aloarte.shopwise.presentation.ui.theme.MugBackground
import com.aloarte.shopwise.presentation.ui.theme.TshirtBackground
import com.aloarte.shopwise.presentation.ui.theme.VoucherBackground

@Composable
fun GridContent(products: List<ProductBo>, onItemClicked: (ProductBo, Int) -> Unit) {
    TitleText(stringResource(id = R.string.list_products_title))

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        items(products.size) {
            ProductItem(products[it], onItemClicked = onItemClicked)
        }
    }
}

@Composable
fun ProductItem(product: ProductBo, onItemClicked: (ProductBo, Int) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AddProductDialog { quantity ->
            quantity?.let {
                onItemClicked.invoke(product, quantity)
            }
            showDialog = false
        }
    }


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
                    contentDescription = stringResource(id = R.string.img_desc_product)
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
                    .clickable {
                        showDialog = true
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    colorFilter = ColorFilter.tint(Color.Black),
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp),
                    painter = painterResource(id = R.drawable.ic_add_product),
                    contentDescription = stringResource(id = R.string.img_desc_add_icon)
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
