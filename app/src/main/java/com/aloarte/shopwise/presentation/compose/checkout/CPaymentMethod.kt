package com.aloarte.shopwise.presentation.compose.checkout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloarte.shopwise.R
import com.aloarte.shopwise.presentation.compose.enums.PaymentMethodType

@Composable
fun PaymentMethod(
    paymentMethod: PaymentMethodType,
    onPaymentSelected: (PaymentMethodType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(180.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontSize = 24.sp,
            color = Color.Black,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.ExtraLight,
            text = stringResource(id = R.string.checkout_choose_payment_title)
        )
        Spacer(modifier = Modifier.height(15.dp))
        PaymentMethodSelectorRow(selected = paymentMethod, onPaymentSelected)
    }
}

@Composable
fun PaymentMethodSelectorRow(
    selected: PaymentMethodType,
    onPaymentSelected: (PaymentMethodType) -> Unit
) {
    val paymentMethods = listOf(
        PaymentMethodType.Mastercard,
        PaymentMethodType.Visa,
        PaymentMethodType.Paypal
    )
    Row(modifier = Modifier.fillMaxWidth()) {
        paymentMethods.forEach { paymentMethod ->
            Column(
                Modifier
                    .border(
                        border = BorderStroke(
                            1.5.dp,
                            if (paymentMethod == selected) MaterialTheme.colorScheme.primary else Color.Black
                        ),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .height(80.dp)
                    .width(80.dp)
                    .clickable {
                        onPaymentSelected.invoke(paymentMethod)
                    }.padding(5.25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp),
                    painter = painterResource(id = getPaymentMethodIcon(paymentMethod)),
                    contentDescription = stringResource(id = R.string.img_desc_payment_method)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

fun getPaymentMethodIcon(paymentMethod: PaymentMethodType) = when (paymentMethod) {
    PaymentMethodType.Mastercard -> R.drawable.ic_mastercard
    PaymentMethodType.Visa -> R.drawable.ic_visa
    PaymentMethodType.Paypal -> R.drawable.ic_paypal
}