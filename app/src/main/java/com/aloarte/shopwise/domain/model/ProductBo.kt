package com.aloarte.shopwise.domain.model

import androidx.annotation.DrawableRes
import com.aloarte.shopwise.domain.enums.ProductType

data class ProductBo(
    val id: String = "0",
    val type: ProductType,
    val code: String,
    val name: String,
    val price: Double,
    val description: String = "",
    @DrawableRes val imageResource: Int = 0
)
