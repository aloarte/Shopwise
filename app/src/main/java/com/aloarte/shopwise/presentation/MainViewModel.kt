package com.aloarte.shopwise.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aloarte.shopwise.domain.ProductBo
import com.aloarte.shopwise.domain.ShopwiseProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ShopwiseProductsRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState())

    fun fetchItems() {
        viewModelScope.launch {
            val data = repository.fetchProducts()
            _state.update {
                it.copy(
                    productList = data
                )
            }
        }
    }

    fun addItemToCart(product: ProductBo, quantity: Int = 1) {
        _state.value.cart.addItem(product, quantity)
        _state.update {
            it.copy(
                cart = _state.value.cart,
                cartValue = _state.value.cart.checkout()
            )
        }
    }

}