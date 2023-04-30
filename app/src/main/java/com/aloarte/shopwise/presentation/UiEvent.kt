package com.aloarte.shopwise.presentation

import com.aloarte.shopwise.domain.ProductBo

sealed interface UiEvent {
    data class AddProduct(val product: ProductBo, val quantity: Int) : UiEvent

    data class ReplaceProductQuantity(val product: ProductBo, val quantity: Int) : UiEvent

    data class RemoveProduct(val product: ProductBo) : UiEvent

    data class OpenDetail(val productCode:String) : UiEvent

    data class  GoPayment(val price:Double) : UiEvent

    object GoCheckout : UiEvent

    object GoList : UiEvent

}