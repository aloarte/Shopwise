package com.aloarte.shopwise.presentation.compose.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aloarte.shopwise.R
import com.aloarte.shopwise.presentation.compose.navigation.Screen

@Composable
fun CheckoutRow(navController: NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate(Screen.PaymentScreen.route) }) {
            Text(text = stringResource(id = R.string.list_checkout_btn))
        }
    }
}
