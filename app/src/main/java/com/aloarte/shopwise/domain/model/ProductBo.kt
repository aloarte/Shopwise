package com.aloarte.shopwise.domain.model

import com.aloarte.shopwise.domain.enums.ProductType

data class ProductBo(
    val type: ProductType,
    val code: String,
    val name: String,
    val price: Double,
    val description: String?=null
)
