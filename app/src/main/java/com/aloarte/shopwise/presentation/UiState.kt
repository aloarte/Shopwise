package com.aloarte.shopwise.presentation

import com.aloarte.shopwise.data.Constants.DISCOUNTED_TSHIRT_PRICE
import com.aloarte.shopwise.data.Constants.MUG_PRICE
import com.aloarte.shopwise.data.Constants.TSHIRT_PRICE
import com.aloarte.shopwise.data.Constants.VOUCHER_PRICE
import com.aloarte.shopwise.domain.ProductBo
import com.aloarte.shopwise.domain.ShoppingCart
import com.aloarte.shopwise.domain.ShoppingCartParams

data class UiState(
    val cart: ShoppingCart = ShoppingCart(
        ShoppingCartParams(
            voucherDefaultPrice = VOUCHER_PRICE,
            tshirtDefaultPrice = TSHIRT_PRICE,
            discountedTshirtPrice = DISCOUNTED_TSHIRT_PRICE,
            mugDefaultPrice = MUG_PRICE,
            voucherDiscountThreshold = 3,
            tshirtDiscountThreshold = 3
        )
    ),
    val productList: List<ProductBo> = emptyList(),
    val cartValue: Double = 0.0,
    val cartSize:Int=0
)