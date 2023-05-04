package com.aloarte.shopwise.presentation.compose.result

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
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
import com.aloarte.shopwise.domain.model.CardBo
import com.aloarte.shopwise.domain.model.PurchaseDataItem
import com.aloarte.shopwise.presentation.UiState
import com.aloarte.shopwise.presentation.compose.commons.PurchaseSummaryRow
import com.aloarte.shopwise.presentation.compose.commons.TitleText
import com.aloarte.shopwise.presentation.compose.enums.PaymentMethodType

@Composable
fun ResultScreenLoaded(state: UiState, onContinue: () -> Unit) {
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
            TitleText(title = stringResource(id = R.string.result_title))
            Spacer(modifier = Modifier.height(40.dp))
            ResultOrderSummary(state)
        }

        ThanksAndContinueButton(Modifier.align(Alignment.BottomEnd), onContinue)

    }
}

@Composable
fun ThanksAndContinueButton(modifier: Modifier = Modifier, onContinue: () -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.width(280.dp),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primaryContainer,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraLight,
            text = stringResource(id = R.string.result_thanks)
        )
        Spacer(modifier = Modifier.height(30.dp))

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
            onClick = onContinue
        ) {
            Text(
                color = MaterialTheme.colorScheme.primaryContainer,
                fontSize = 14.sp,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraLight,
                text = stringResource(id = R.string.result_btn)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }

}

@Composable
fun ResultOrderSummary(state: UiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ResultSelectedProducts(state.purchaseData)
        Spacer(modifier = Modifier.height(40.dp))
        ResultPayment(state.selectedPaymentMethod, state.cards)

    }
}

@Composable
fun ResultSelectedProducts(purchaseData: List<PurchaseDataItem>) {
    PurchaseSummaryRow()
    purchaseData.forEach {
        PurchaseSummaryRow(it)
    }
}

@Composable
fun ResultPayment(paymentMethod: PaymentMethodType, cards: List<CardBo>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {
        Text(
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primaryContainer,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.result_payment_method)
        )
        when (paymentMethod) {
            PaymentMethodType.Paypal -> ResultPaypal()
            PaymentMethodType.Visa, PaymentMethodType.Mastercard -> ResultPaymentCard(cards.find { it.paymentNetworkType.name == paymentMethod.name })
        }
    }
}

@Composable
fun ResultPaymentCard(card: CardBo?) {
    card?.let {
        Text(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primaryContainer,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraLight,
            text = stringResource(
                id = R.string.result_payment_card,
                card.type.name,
                card.paymentNetworkType.name,
                card.cardNumber.takeLast(4)
            )
        )
    }
}

@Composable
fun ResultPaypal() {
    Text(
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.primaryContainer,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.ExtraLight,
        text = stringResource(id = R.string.result_payment_paypal)
    )
}
