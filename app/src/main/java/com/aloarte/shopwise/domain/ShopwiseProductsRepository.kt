package com.aloarte.shopwise.domain

interface ShopwiseProductsRepository {

    suspend fun fetchProducts(): List<ProductBo>
}