package com.aloarte.shopwise.domain.repositories

import com.aloarte.shopwise.domain.model.ProductBo

interface ShopwiseProductsRepository {

    suspend fun fetchProducts(): List<ProductBo>
}