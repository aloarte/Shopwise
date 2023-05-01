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
import com.aloarte.shopwise.presentation.compose.checkout.PaymentScreen
import com.aloarte.shopwise.presentation.compose.detail.DetailScreen
import com.aloarte.shopwise.presentation.compose.list.ListScreen
import com.aloarte.shopwise.presentation.compose.result.ResultScreen

@Composable
fun NavigationComponent(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    val navController = rememberNavController()
    val onInnerEventTriggered: (UiEvent) -> Unit = { event ->
        when (event) {
            UiEvent.GoList -> navController.navigate(Screen.ListScreen.route)
            UiEvent.GoCart -> navController.navigate(Screen.CheckoutScreen.route)
            is UiEvent.GoCheckout -> navController.navigate(
                route = Screen.PaymentScreen.withArgs(event.price.toString())
            )

            UiEvent.GoResult -> {
                // Navigate and pass the event to the main activity so it can empty the cart
                navController.navigate(Screen.CheckoutScreen.route)
                onEventTriggered.invoke(event)
            }

            is UiEvent.OpenDetail -> navController.navigate(
                route = Screen.DetailScreen.withArgs(event.productCode)
            )

            else -> onEventTriggered.invoke(event)
        }

    }
    NavHost(navController = navController, startDestination = Screen.PaymentScreen.route) {
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
        composable(route = Screen.CheckoutScreen.route) {
            CartScreen(
                state = state,
                onEventTriggered = onInnerEventTriggered
            )

        }
        composable(
            route = Screen.PaymentScreen.route /*+ "/{price}",
            arguments = listOf(
                navArgument("price") {
                    type = NavType.StringType
                    defaultValue = "0.0"
                })*/
        ) { entry ->
            PaymentScreen(
//                price = entry.arguments?.getString("price")?.toDouble(),
                price = 120.0,
                state = state,
                onEventTriggered = onInnerEventTriggered
            )

        }
        composable(route = Screen.ResultScreen.route) {
            ResultScreen(navController = navController)
        }
    }
}