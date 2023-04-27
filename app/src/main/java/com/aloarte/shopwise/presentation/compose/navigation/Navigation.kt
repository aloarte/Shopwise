package com.aloarte.shopwise.presentation.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aloarte.shopwise.presentation.compose.cart.CartScreen
import com.aloarte.shopwise.presentation.compose.detail.DetailScreen
import com.aloarte.shopwise.presentation.compose.list.ListScreen
import com.aloarte.shopwise.presentation.compose.payment.PaymentScreen
import com.aloarte.shopwise.presentation.compose.result.ResultScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.DetailScreen.route){

        composable(route =  Screen.ListScreen.route){
            ListScreen(navController = navController)
        }
        composable(route =  Screen.DetailScreen.route){
            DetailScreen(navController = navController)

        }
        composable(route =  Screen.CartScreen.route){
            CartScreen(navController = navController)

        }
        composable(route =  Screen.PaymentScreen.route){
            PaymentScreen(navController = navController)

        }
        composable(route =  Screen.ResultScreen.route){
            ResultScreen(navController = navController)
        }
    }
}