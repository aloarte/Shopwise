package com.aloarte.shopwise.data.dto

import androidx.annotation.DrawableRes

data class ProductDto(
    val id: String? = null,
    val code: String,
    val name: String,
    val price: Double,
    val description: String? = null,
    @DrawableRes val imageResource: Int? = null
)
