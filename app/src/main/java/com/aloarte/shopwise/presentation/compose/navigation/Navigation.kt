package com.aloarte.shopwise.presentation.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aloarte.shopwise.presentation.UiEvent
import com.aloarte.shopwise.presentation.UiState
import com.aloarte.shopwise.presentation.compose.cart.CartScreen
import com.aloarte.shopwise.presentation.compose.detail.DetailScreen
import com.aloarte.shopwise.presentation.compose.list.ListScreen
import com.aloarte.shopwise.presentation.compose.payment.PaymentScreen
import com.aloarte.shopwise.presentation.compose.result.ResultScreen

@Composable
fun NavigationComponent(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    val navController = rememberNavController()
    val onInnerEventTriggered:(UiEvent)->Unit = {event->
        when (event) {
            UiEvent.GoCheckout -> navController.navigate(Screen.PaymentScreen.route)
            UiEvent.GoList -> navController.navigate(Screen.ListScreen.route)
            is UiEvent.OpenDetail -> navController.navigate(
                route = Screen.DetailScreen.withArgs(event.productCode)
            )

            else -> onEventTriggered.invoke(event)
        }

    }
    NavHost(navController = navController, startDestination = Screen.ListScreen.route) {

        composable(route = Screen.ListScreen.route) {
            ListScreen(
                state = state,
                onEventTriggered = onInnerEventTriggered
            )
        }
        composable(
            route = Screen.DetailScreen.route + "/{productType}",
            arguments = listOf(
                navArgument("productType") {
                    type = NavType.StringType
                    defaultValue = "UNKNOWN"
                    nullable = true
                })
        ) { entry ->
            DetailScreen(
                productType = entry.arguments?.getString("productType"),
                state = state,
                onEventTriggered = onInnerEventTriggered
            )

        }
        composable(route = Screen.CartScreen.route) {
            CartScreen(navController = navController)

        }
        composable(route = Screen.PaymentScreen.route) {
            PaymentScreen(navController = navController)

        }
        composable(route = Screen.ResultScreen.route) {
            ResultScreen(navController = navController)
        }
    }
}