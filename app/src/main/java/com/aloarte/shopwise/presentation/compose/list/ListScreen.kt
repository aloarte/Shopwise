@file:OptIn(ExperimentalMaterial3Api::class)

package com.aloarte.shopwise.presentation.compose.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aloarte.shopwise.R
import com.aloarte.shopwise.domain.ProductBo
import com.aloarte.shopwise.domain.ProductType
import com.aloarte.shopwise.presentation.compose.TitleText
import com.aloarte.shopwise.presentation.compose.navigation.Screen
import com.aloarte.shopwise.presentation.ui.theme.MugBackground
import com.aloarte.shopwise.presentation.ui.theme.TshirtBackground
import com.aloarte.shopwise.presentation.ui.theme.VoucherBackground

@Composable
fun ListScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 15.dp)
            .scrollable(state = scrollState, orientation = Orientation.Vertical)
    ) {
        IconsRow()
        Spacer(modifier = Modifier.height(10.dp))
        SearchRow()
        Spacer(modifier = Modifier.height(10.dp))
        FilterRow()
        Spacer(modifier = Modifier.height(10.dp))
        GridContent()
        Spacer(modifier = Modifier.height(10.dp))
        CheckoutRow(navController)
    }
}


@Composable
fun IconsRow() {
}

@Composable
fun SearchRow() {
    var searchText by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
    ) {
        TextField(value = searchText, onValueChange = { searchText = it }, Modifier.fillMaxWidth())
    }
}


@Composable
fun FilterRow() {
}

@Composable
fun GridContent() {

    val products = listOf(
        ProductBo(
            type = ProductType.Voucher,
            code = "VOUCHER",
            name = "Cabify Voucher",
            price = 5.0
        ),
        ProductBo(
            type = ProductType.Tshirt,
            code = "TSHIRT",
            name = "Cabify T-shirt",
            price = 20.0
        ), ProductBo(
            type = ProductType.Mug,
            code = "MUG",
            name = "Cabify Coffee Mug",
            price = 7.5
        )
    )

    TitleText("PRODUCTS")

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)

        ) {
        items(products.size) {
            ProductItem(products[it])
        }
    }
}

@Composable
fun ProductItem(product: ProductBo) {
    Column(verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
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
                    contentDescription = "Product image"
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(Modifier.fillMaxWidth().padding(horizontal = 10.dp)) {
            Column(Modifier.align(Alignment.CenterStart)) {
                Text(
                    fontSize = 16.sp,
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
            Image(
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)
                    .align(Alignment.CenterEnd),
                painter = painterResource(id = R.drawable.ic_like),
                contentDescription = "Product image"
            )
        }
    }

}


fun getProductImage(productType: ProductType) = when (productType) {
    ProductType.Voucher -> R.drawable.ic_voucher
    ProductType.Tshirt -> R.drawable.ic_tshirt
    ProductType.Mug -> R.drawable.ic_mug
}

fun getProductBackground(productType: ProductType) = when (productType) {
    ProductType.Voucher -> VoucherBackground
    ProductType.Tshirt -> TshirtBackground
    ProductType.Mug -> MugBackground
}

@Composable
fun CheckoutRow(navController: NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate(Screen.PaymentScreen.route) }) {
            Text(text = "Checkout")
        }
    }
}



