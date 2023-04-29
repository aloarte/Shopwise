package com.aloarte.shopwise.presentation

import com.aloarte.shopwise.domain.ProductBo

sealed interface UiEvent {
    data class AddProduct(val product: ProductBo, val quantity: Int) : UiEvent

    data class OpenDetail(val productCode:String) : UiEvent

    object GoCheckout : UiEvent

    object GoList : UiEvent

}