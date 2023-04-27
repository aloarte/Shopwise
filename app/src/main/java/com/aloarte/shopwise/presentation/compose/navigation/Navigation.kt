package com.aloarte.shopwise.presentation.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aloarte.shopwise.presentation.compose.cart.CartScreen
import com.aloarte.shopwise.presentation.compose.detail.DetailScreen
import com.aloarte.shopwise.presentation.compose.list.ListScreen
import com.aloarte.shopwise.presentation.compose.payment.PaymentScreen
import com.aloarte.shopwise.presentation.compose.result.ResultScreen

@Composable
fun NavigationComponent() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ListScreen.route) {

        composable(route = Screen.ListScreen.route) {
            ListScreen(navController = navController)
        }
        composable(
            route = Screen.DetailScreen.route + "/{productType}",
            arguments = listOf(
                navArgument("productType") {
                    type = NavType.StringType
                    defaultValue = "UNKNOWN"
                    nullable = true
                })
        ) { entry->
            DetailScreen(productType = entry.arguments?.getString("productType"))

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