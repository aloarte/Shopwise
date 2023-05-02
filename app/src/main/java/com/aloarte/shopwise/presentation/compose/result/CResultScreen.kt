package com.aloarte.shopwise.presentation.compose.result

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.aloarte.shopwise.presentation.UiConstants.LOADING_PURCHASE_DELAY_TIME
import com.aloarte.shopwise.presentation.UiEvent
import com.aloarte.shopwise.presentation.UiState
import kotlinx.coroutines.delay

@Composable
fun ResultScreen(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    // Setup a delay launched effect to display the ResultScreenLoading simulating a loading process
    var animEnded by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        delay(LOADING_PURCHASE_DELAY_TIME)
        animEnded = true
    }
    //The back on this screen will go to the first screen as the cart will be empty by now
    BackHandler {
        if (animEnded) {
            onEventTriggered.invoke(UiEvent.GoList)
        }
    }
    if (animEnded) {
        ResultScreenLoaded(state) {
            onEventTriggered.invoke(UiEvent.GoList)
        }
    } else {
        ResultScreenLoading()
    }
}