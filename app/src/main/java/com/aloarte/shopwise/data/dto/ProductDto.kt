package com.aloarte.shopwise.data.dto

import androidx.annotation.DrawableRes

data class ProductDto(
    val id: String = "0",
    val code: String,
    val name: String,
    val price: Double,
    val description: String = "",
    @DrawableRes val imageResource: Int = 0
)
