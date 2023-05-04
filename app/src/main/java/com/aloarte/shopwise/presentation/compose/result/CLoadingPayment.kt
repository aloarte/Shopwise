package com.aloarte.shopwise.presentation.compose.result

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.aloarte.shopwise.R
import com.aloarte.shopwise.presentation.UiConstants
import com.aloarte.shopwise.presentation.compose.commons.TitleText


@Composable
fun ResultScreenLoading() {
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
            TitleText(
                title = stringResource(id = R.string.checkout_title)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.width(280.dp),
                text = stringResource(id = R.string.result_loading_payment_message),
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.ExtraLight

            )

        }
        LoadingPurchaseLottie(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun LoadingPurchaseLottie(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_tpv_anim))
    Surface(
        modifier
            .height(400.dp)
            .width(400.dp)
    ) {
        LottieAnimation(
            composition,
            restartOnPlay = true,
            iterations = UiConstants.LOADING_PURCHASE_LOTTIE_ITERATIONS
        )
    }
}