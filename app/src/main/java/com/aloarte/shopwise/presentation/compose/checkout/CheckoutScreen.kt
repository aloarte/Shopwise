package com.aloarte.shopwise.presentation.compose.checkout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aloarte.shopwise.R
import com.aloarte.shopwise.presentation.UiEvent
import com.aloarte.shopwise.presentation.compose.commons.TitleRow

@Composable
fun PaymentScreen(price: Double?, onEventTriggered: (UiEvent) -> Unit) {
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
            TitleRow(stringResource(id = R.string.checkout_title)){
                onEventTriggered.invoke(UiEvent.GoCart)
            }

        }
//        TotalAndPaymentRow(
//            modifier = Modifier.align(Alignment.BottomCenter),
//            state = state,
//            onEventTriggered = onEventTriggered
//        )
    }

}