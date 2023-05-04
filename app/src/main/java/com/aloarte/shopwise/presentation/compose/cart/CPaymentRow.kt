package com.aloarte.shopwise.presentation.compose.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloarte.shopwise.R
import com.aloarte.shopwise.presentation.compose.commons.PriceRow
import com.aloarte.shopwise.presentation.compose.enums.PriceRowType


@Composable
fun TotalAndPaymentRow(
    modifier: Modifier,
    price: Double = 0.0,
    onContinueClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        PriceRow(
            label = stringResource(id = R.string.cart_label_total),
            price = price,
            type = PriceRowType.Checkout
        )
        Spacer(Modifier.height(20.dp))
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = Color.Transparent
            ),
            border = BorderStroke(
                1.5.dp,
                MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(10.dp),
            onClick = onContinueClicked
        ) {
            Text(
                color = MaterialTheme.colorScheme.primaryContainer,
                fontSize = 14.sp,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraLight,
                text = stringResource(id = R.string.checkout_btn)
            )
        }
    }
}
