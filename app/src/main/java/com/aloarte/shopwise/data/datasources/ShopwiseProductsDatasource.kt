package com.aloarte.shopwise.data.datasources

import com.aloarte.shopwise.data.dto.ApiResult
import com.aloarte.shopwise.data.dto.ProductDto

interface ShopwiseProductsDatasource {

    suspend fun fetchProducts(): ApiResult<List<ProductDto>>

}