package com.aloarte.shopwise.domain.repositories

import com.aloarte.shopwise.domain.model.PurchaseDataItem
import com.aloarte.shopwise.domain.model.ProductBo

interface ShopwiseProductsRepository {

    suspend fun fetchProducts(): List<ProductBo>

    fun getPurchaseData(products: List<Pair<ProductBo, Int>>): List<PurchaseDataItem>

}