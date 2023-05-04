package com.aloarte.shopwise.presentation.compose.navigation

sealed class Screen(val route: String) {
    object ListScreen : Screen("list_screen")
    object DetailScreen : Screen("detail_screen")
    object CheckoutScreen : Screen("checkout_screen")
    object PaymentScreen : Screen("payment_screen")
    object ResultScreen : Screen("result_screen")

    fun withArgs(vararg args: String) = buildString {
        append(route)
        args.forEach { arg ->
            append("/$arg")

        }
    }
}