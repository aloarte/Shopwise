package com.aloarte.shopwise.data

import com.aloarte.shopwise.data.dto.ProductDto

data class ProductsResponse(
    var products: List<ProductDto>? = null
)