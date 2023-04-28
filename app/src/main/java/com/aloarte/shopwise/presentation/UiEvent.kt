package com.aloarte.shopwise.presentation

import com.aloarte.shopwise.domain.ProductBo

sealed interface UiEvent {
    data class AddProduct(val product: ProductBo, val quantity: Int) : UiEvent
    object GoCheckout : UiEvent
}