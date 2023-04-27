package com.aloarte.shopwise.presentation.compose.navigation

sealed class Screen(val route: String) {
    object ListScreen : Screen("list_screen")
    object DetailScreen : Screen("detail_screen")
    object CartScreen : Screen("cart_screen")
    object PaymentScreen : Screen("payment_screen")
    object ResultScreen : Screen("result_screen")
}