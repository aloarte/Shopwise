package com.aloarte.shopwise.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.aloarte.shopwise.presentation.compose.navigation.NavigationComponent
import com.aloarte.shopwise.presentation.ui.theme.ShopwiseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = viewModel.state.collectAsState().value
            ShopwiseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationComponent(state) { event ->
                        when (event) {
                            is UiEvent.AddProduct -> {
                                viewModel.addItemToCart(
                                    product = event.product,
                                    quantity = event.quantity
                                )
                            }

                            is UiEvent.ReplaceProductQuantity -> {
                                viewModel.addItemToCart(
                                    replace = true,
                                    product = event.product,
                                    quantity = event.quantity
                                )
                            }

                            is UiEvent.RemoveProduct -> {
                                viewModel.removeItemFromCart(product = event.product)
                            }

                            is UiEvent.ChangePayment ->{
                                viewModel.changePaymentType(event.type)
                            }

                            UiEvent.GoResult ->{
                                viewModel.clearCartAndState()
                            }

                            else -> {}

                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchItems(false)
    }
}