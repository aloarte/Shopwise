package com.aloarte.shopwise.presentation

import com.aloarte.shopwise.domain.model.ProductBo
import com.aloarte.shopwise.presentation.compose.enums.PaymentMethodType

sealed interface UiEvent {
    data class AddProduct(val product: ProductBo, val quantity: Int) : UiEvent

    data class ReplaceProductQuantity(val product: ProductBo, val quantity: Int) : UiEvent

    data class RemoveProduct(val product: ProductBo) : UiEvent

    data class ChangePayment(val type: PaymentMethodType) : UiEvent

    data class OpenDetail(val productCode:String) : UiEvent

    object GoList : UiEvent

    object GoCart : UiEvent

    data class  GoCheckout(val price:Double) : UiEvent

    object GoResult : UiEvent

}