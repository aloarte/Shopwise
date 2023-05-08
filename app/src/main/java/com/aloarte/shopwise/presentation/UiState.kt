package com.aloarte.shopwise.presentation

import com.aloarte.shopwise.data.Constants.DISCOUNTED_VALUE_PRICE
import com.aloarte.shopwise.data.Constants.MUG_PRICE
import com.aloarte.shopwise.data.Constants.TSHIRT_PRICE
import com.aloarte.shopwise.data.Constants.VOUCHER_PRICE
import com.aloarte.shopwise.domain.model.PurchaseDataItem
import com.aloarte.shopwise.domain.cart.ShoppingCart
import com.aloarte.shopwise.domain.cart.ShoppingCartParams
import com.aloarte.shopwise.domain.model.CardBo
import com.aloarte.shopwise.domain.model.ProductBo
import com.aloarte.shopwise.presentation.compose.enums.PaymentMethodType

data class UiState(
    val cart: ShoppingCart = ShoppingCart(
        ShoppingCartParams(
            voucherDefaultPrice = VOUCHER_PRICE,
            tshirtDefaultPrice = TSHIRT_PRICE,
            discountedTshirtPrice = DISCOUNTED_VALUE_PRICE,
            mugDefaultPrice = MUG_PRICE,
            voucherDiscountThreshold = 3,
            tshirtDiscountThreshold = 3
        )
    ),
    val cards: List<CardBo> = emptyList(),
    val selectedPaymentMethod: PaymentMethodType = PaymentMethodType.Mastercard,
    val productList: List<ProductBo> = emptyList(),
    val cartValue: Double = 0.0,
    val cartSize: Int = 0,
    val purchaseData: List<PurchaseDataItem> = emptyList()
)