package com.aloarte.shopwise.presentation.compose.checkout

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloarte.shopwise.R
import com.aloarte.shopwise.domain.enums.CardPaymentNetworkType
import com.aloarte.shopwise.domain.model.CardBo
import com.aloarte.shopwise.presentation.UiConstants
import com.aloarte.shopwise.presentation.UiConstants.USER_CARD_ANIM_TIME
import com.aloarte.shopwise.presentation.UiConstants.USER_CARD_SIZE
import com.aloarte.shopwise.presentation.UiState
import com.aloarte.shopwise.presentation.compose.commons.CardItemWithLabel
import com.aloarte.shopwise.presentation.compose.enums.PaymentMethodType

@Composable
fun CardSection(state: UiState) {
    var payment by remember { mutableStateOf(PaymentMethodType.Mastercard) }
    var shutterAlpha by remember { mutableStateOf(1f) }
    LaunchedEffect(state) {
        if (state.selectedPaymentMethod != payment) {
            animate(1f, 0f, animationSpec = tween(0)) { value, _ -> shutterAlpha = value }
            payment = state.selectedPaymentMethod
            animate(0f, 1f, animationSpec = tween(1000)) { value, _ -> shutterAlpha = value }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(260.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primaryContainer,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.ExtraLight,
            text = stringResource(id = R.string.checkout_selected_payment_title)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier.alpha(shutterAlpha)
        ) {
            when (val selectedPayment = payment) {
                PaymentMethodType.Paypal -> PaypalCard()
                PaymentMethodType.Mastercard, PaymentMethodType.Visa -> UserCard(state.cards.find { it.paymentNetworkType.toString() == selectedPayment.toString() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(card: CardBo?) {

    var rotated by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(USER_CARD_ANIM_TIME)
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(USER_CARD_ANIM_TIME)
    )

    val animateBack by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
        animationSpec = tween(USER_CARD_ANIM_TIME)
    )

    val cardModifier = Modifier
        .graphicsLayer {
            alpha = if (rotated) animateBack else animateFront
            rotationY = rotation
            cameraDistance = 8 * density
        }

    card?.let { cardValue ->
        Card(
            shape = RoundedCornerShape(25.dp),
            onClick = { rotated = !rotated },
            modifier = Modifier
                .height(USER_CARD_SIZE.dp)
                .width((USER_CARD_SIZE * UiConstants.ID_CARD_AR).dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            if (rotated) CardBack(card = cardValue, modifier = cardModifier)
            else CardFront(card = cardValue, modifier = cardModifier)
        }
    }
}

@Composable
fun CardFront(card: CardBo, modifier: Modifier = Modifier) {
    Box(
        modifier
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
            text = stringResource(id = R.string.checkout_user_card_type, card.type.name)
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
        CardItemWithLabel(
            itemValue = card.cardNumber,
            label = stringResource(id = R.string.checkout_user_card_number_label),
            modifier = Modifier.align(Alignment.CenterEnd)
        )

        CardItemWithLabel(
            itemValue = card.ownerName,
            label = stringResource(id = R.string.checkout_user_card_name_label),
            modifier = Modifier.align(Alignment.BottomStart)
        )
        Image(
            painter = painterResource(
                when (card.paymentNetworkType) {
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

@Composable
fun CardBack(card: CardBo, modifier: Modifier = Modifier) {
    Box(
        modifier
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
            text = stringResource(id = R.string.checkout_user_card_type, card.type.name)
        )

        CardItemWithLabel(
            hideValue = true,
            itemValue = card.ccv,
            label = stringResource(id = R.string.checkout_user_card_ccv_label),
            modifier = Modifier.align(Alignment.BottomStart)
        )

        CardItemWithLabel(
            itemValue = card.expirationDate,
            label = stringResource(id = R.string.checkout_user_card_expiration_label),
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun PaypalCard() {
    Card(
        shape = RoundedCornerShape(25.dp),
        modifier = Modifier
            .height(USER_CARD_SIZE.dp)
            .width((USER_CARD_SIZE * UiConstants.ID_CARD_AR).dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(25.dp)
        ) {
            CardItemWithLabel(
                itemValue = stringResource(id = R.string.checkout_paypal_mail_value),
                label = stringResource(id = R.string.checkout_paypal_mail_label),
                modifier = Modifier.align(Alignment.TopStart)
            )

        }
    }
}

