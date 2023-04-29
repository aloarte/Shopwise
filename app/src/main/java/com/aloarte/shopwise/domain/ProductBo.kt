package com.aloarte.shopwise.domain

data class ProductBo(
    val type: ProductType,
    val code: String,
    val name: String,
    val price: Double,
    val description: String?
)
