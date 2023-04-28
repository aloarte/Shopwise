package com.aloarte.shopwise.presentation.compose.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun CheckoutRow(onButtonClicked: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(10.dp),
            onClick = onButtonClicked
        ) {

            Text(
                color = Color.Black,
                fontSize = 14.sp,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraLight,
                text = stringResource(id = R.string.list_checkout_btn)
            )
        }
    }
}
