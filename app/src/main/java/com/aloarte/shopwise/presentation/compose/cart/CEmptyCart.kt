package com.aloarte.shopwise.presentation.compose.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.aloarte.shopwise.R
import com.aloarte.shopwise.presentation.UiConstants.EMPTY_CART_LOTTIE_ITERATIONS

@Composable
fun EmptyCart(onBackPressed: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 20.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .width(280.dp),
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.ExtraLight,
            text = "Your cart is empty. Come back again when you have any item to complete the purchase."
        )
        EmptyCartLottie(Modifier.align(Alignment.Center))
        OutlinedButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
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
            onClick = onBackPressed
        ) {
            Text(
                color = Color.Black,
                fontSize = 14.sp,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraLight,
                text = stringResource(id = R.string.cart_empty_btn)
            )
        }
    }

}

@Composable
fun EmptyCartLottie(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_cart))
    Surface(
        modifier
            .height(400.dp)
            .width(400.dp)
    ) {
        LottieAnimation(
            composition,
            restartOnPlay = true,
            iterations = EMPTY_CART_LOTTIE_ITERATIONS
        )
    }

}