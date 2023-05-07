package com.aloarte.shopwise.domain.repositories

import com.aloarte.shopwise.domain.model.ProductBo
import com.aloarte.shopwise.domain.model.PurchaseDataItem

interface ShopwiseProductsRepository {

    suspend fun fetchProducts(remote: Boolean = true): List<ProductBo>

    fun getPurchaseData(products: List<Pair<ProductBo, Int>>): List<PurchaseDataItem>

}