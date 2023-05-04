package com.aloarte.shopwise.presentation.compose.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.aloarte.shopwise.R
import com.aloarte.shopwise.presentation.UiConstants

@Composable
fun IconsRow(cartSize: Int, onGoToCheckout: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        if (cartSize > 0) {
            ArrowLottie(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(horizontal = 30.dp)
            )
        }
        CartIcon(
            cartSize,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .height(30.dp)
                .width(30.dp)
                .clickable(onClick = onGoToCheckout),
        )
    }
}

@Composable
fun CartIcon(cartSize: Int, modifier: Modifier) {
    Box(modifier = modifier) {
        Icon(
            modifier = Modifier
                .height(25.dp)
                .width(25.dp)
                .align(Alignment.Center),
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = stringResource(id = R.string.img_desc_cart_icon)
        )
        if (cartSize > 0) {
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .height(15.dp)
                    .width(15.dp)
                    .align(Alignment.TopEnd)
                    .background(Color.Red),

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    color = Color.White,
                    fontSize = 10.sp,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraLight,
                    text = cartSize.toString()
                )
            }
        }

    }

}

@Composable
fun ArrowLottie(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.arrow_anim))
    Surface(
        modifier
            .height(40.dp)
            .width(40.dp)
    ) {
        LottieAnimation(
            composition,
            restartOnPlay = true,
            iterations = UiConstants.ARROW_LOTTIE_ITERATIONS,
        )
    }

}