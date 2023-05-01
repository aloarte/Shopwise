package com.aloarte.shopwise.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aloarte.shopwise.domain.model.ProductBo
import com.aloarte.shopwise.domain.repositories.CardsRepository
import com.aloarte.shopwise.domain.repositories.ShopwiseProductsRepository
import com.aloarte.shopwise.presentation.compose.enums.PaymentMethodType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productsRepository: ShopwiseProductsRepository,
    private val cardsRepository: CardsRepository
) :    ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState())

    fun fetchItems() {
        viewModelScope.launch {
            val products = productsRepository.fetchProducts()
            val cards = cardsRepository.fetchUserCards()
            _state.update {
                it.copy(
                    productList = products,
                    cards = cards
                )
            }
        }
    }

    fun addItemToCart(replace: Boolean = false, product: ProductBo, quantity: Int) {
        if (replace) _state.value.cart.resetItem(product)
        _state.value.cart.addItem(product, quantity)

        _state.update {
            with(_state.value.cart) {
                it.copy(
                    cart = this,
                    cartValue = checkout(),
                    cartSize = productsNumber()
                )
            }

        }
    }

    fun removeItemFromCart(product: ProductBo) {
        _state.value.cart.removeItem(product)
        refreshCartState()
    }

    fun clearCartAndState() {
        _state.value.cart.clearCart()
        refreshCartState()
    }

    private fun refreshCartState() {
        _state.update {
            with(_state.value.cart) {
                it.copy(
                    cart = this,
                    cartValue = checkout(),
                    cartSize = productsNumber()
                )
            }
        }
    }

    fun changePaymentType(type: PaymentMethodType) {
        _state.update {
            it.copy(
                selectedPaymentMethod = type,
            )
        }
    }

}