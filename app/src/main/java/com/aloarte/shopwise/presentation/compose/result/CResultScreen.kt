package com.aloarte.shopwise.presentation.compose.result

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.aloarte.shopwise.presentation.UiConstants
import com.aloarte.shopwise.presentation.UiConstants.LOADING_PURCHASE_DELAY_TIME
import com.aloarte.shopwise.presentation.UiEvent
import com.aloarte.shopwise.presentation.UiState
import com.aloarte.shopwise.presentation.compose.commons.TitleText
import kotlinx.coroutines.delay

@Composable
fun ResultScreen(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    var animEnded by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        delay(LOADING_PURCHASE_DELAY_TIME)
        animEnded = true
    }
    BackHandler {
        if(animEnded){
            onEventTriggered.invoke(UiEvent.GoList)
        }
    }
    if (animEnded) {
        ResultScreenLoaded(state){
            onEventTriggered.invoke(UiEvent.GoList)
        }
    } else {
        ResultScreenLoading()
    }


}

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


        }
        LoadingPurchaseLottie(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .align(Alignment.BottomCenter))

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

@Composable
fun ResultScreenLoaded(state: UiState,onContinue: () -> Unit) {
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


        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = Color.LightGray,
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
                color = Color.Black,
                fontSize = 14.sp,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraLight,
                text = stringResource(id = R.string.checkout_btn)
            )
        }
    }
}