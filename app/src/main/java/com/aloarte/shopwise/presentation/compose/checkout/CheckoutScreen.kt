@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.aloarte.shopwise.presentation.compose.checkout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.aloarte.shopwise.domain.enums.CardPaymentNetworkType
import com.aloarte.shopwise.domain.model.CardBo
import com.aloarte.shopwise.presentation.UiConstants.ID_CARD_AR
import com.aloarte.shopwise.presentation.UiEvent
import com.aloarte.shopwise.presentation.UiState
import com.aloarte.shopwise.presentation.compose.commons.PriceRow
import com.aloarte.shopwise.presentation.compose.commons.TitleRow
import com.aloarte.shopwise.presentation.compose.enums.PriceRowType

@Composable
fun PaymentScreen(price: Double?, state: UiState, onEventTriggered: (UiEvent) -> Unit) {
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
            TitleRow(stringResource(id = R.string.checkout_title)) {
                onEventTriggered.invoke(UiEvent.GoCart)
            }
            CardSection(state.cards)
        }
        TotalAndPaymentRow(
            modifier = Modifier.align(Alignment.BottomCenter),
            price = price ?: 0.0,
            onEventTriggered = onEventTriggered
        )
    }

}

@Composable
fun CardSection(cardList:List<CardBo>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FakeCard(cardList.getOrNull(1))
    }
}

@Composable
fun FakeCard(card:CardBo?) {
    val cardSize = 180
    card?.let{
        Card(
            shape = RoundedCornerShape(25.dp),
            onClick = { },
            modifier = Modifier
                .height(cardSize.dp)
                .width((cardSize * ID_CARD_AR).dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(vertical = 20.dp, horizontal = 30.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.TopStart),
                    fontSize = 20.sp,
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.ExtraLight,
                    text = "${card.type} Card"
                )
                Image(
                    painter = painterResource(R.drawable.ic_nfc),
                    modifier = Modifier
                        .height(15.dp)
                        .width(15.dp)
                        .align(Alignment.TopEnd),
                    colorFilter = ColorFilter.tint(Color.White),
                    contentDescription = stringResource(id = R.string.ic_descr_nfc)
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    fontSize = 30.sp,
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.ExtraLight,
                    text = card.cardNumber
                )

                Text(
                    modifier = Modifier.align(Alignment.BottomStart),
                    fontSize = 20.sp,
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.ExtraLight,
                    text = card.ownerName
                )
                Image(
                    painter = painterResource(
                        when(card.paymentNetworkType){
                            CardPaymentNetworkType.Mastercard -> R.drawable.ic_mastercard_white
                            CardPaymentNetworkType.Visa -> R.drawable.ic_visa_white
                        }
                    ),
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                        .align(Alignment.BottomEnd),
                    contentDescription = stringResource(id = R.string.ic_descr_card_supplier)
                )

            }

        }
    }

}

@Composable
fun TotalAndPaymentRow(
    modifier: Modifier,
    price: Double = 0.0,
    onEventTriggered: (UiEvent) -> Unit
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
                disabledContainerColor = Color.LightGray,
                containerColor = Color.Transparent
            ),
            border = BorderStroke(
                1.5.dp,
                MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(10.dp),
            onClick = { onEventTriggered.invoke(UiEvent.GoResult) }
        ) {
            Text(
                color = Color.Black,
                fontSize = 14.sp,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraLight,
                text = stringResource(id = R.string.checkout_btn)
            )
        }
    }
}
