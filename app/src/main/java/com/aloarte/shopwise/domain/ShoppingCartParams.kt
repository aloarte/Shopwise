package com.aloarte.shopwise.domain

data class ShoppingCartParams(
    val voucherDefaultPrice: Double,
    val tshirtDefaultPrice: Double,
    val discountedTshirtPrice: Double,
    val mugDefaultPrice: Double,
    val voucherDiscountThreshold:Int,
    val tshirtDiscountThreshold:Int
    )
