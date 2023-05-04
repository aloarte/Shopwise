package com.aloarte.shopwise.data.dto

import androidx.annotation.DrawableRes

data class ProductDto(
    val id: String,
    val code: String,
    val name: String,
    val price: Double,
    val description: String,
    @DrawableRes val imageResource: Int
)
