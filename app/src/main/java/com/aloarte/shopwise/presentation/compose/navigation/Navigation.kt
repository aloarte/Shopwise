package com.aloarte.shopwise.presentation.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aloarte.shopwise.presentation.UiEvent
import com.aloarte.shopwise.presentation.UiState
import com.aloarte.shopwise.presentation.compose.checkout.CheckoutScreen
import com.aloarte.shopwise.presentation.compose.detail.DetailScreen
import com.aloarte.shopwise.presentation.compose.list.ListScreen
import com.aloarte.shopwise.presentation.compose.payment.PaymentScreen
import com.aloarte.shopwise.presentation.compose.result.ResultScreen

@Composable
fun NavigationComponent(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    val navController = rememberNavController()
    val onInnerEventTriggered: (UiEvent) -> Unit = { event ->
        when (event) {
            UiEvent.GoCheckout -> navController.navigate(Screen.CheckoutScreen.route)
            UiEvent.GoList -> navController.navigate(Screen.ListScreen.route)
            is UiEvent.GoPayment -> navController.navigate(
                route = Screen.PaymentScreen.withArgs(event.price.toString())
            )

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
        composable(route = Screen.CheckoutScreen.route) {
            CheckoutScreen(
                state = state,
                onEventTriggered = onInnerEventTriggered
            )

        }
        composable(
            route = Screen.PaymentScreen.route + "/{price}",
            arguments = listOf(
                navArgument("price") {
                    type = NavType.StringType
                    defaultValue = "UNKNOWN"
                    nullable = true
                })
        ) { entry ->
            PaymentScreen(
                price = entry.arguments?.getDouble("price"),
                state = state,
                onEventTriggered = onInnerEventTriggered
            )

        }
        composable(route = Screen.ResultScreen.route) {
            ResultScreen(navController = navController)
        }
    }
}