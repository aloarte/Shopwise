package com.aloarte.shopwise.data.repositories

import com.aloarte.shopwise.data.datasources.ShopwiseProductsDatasource
import com.aloarte.shopwise.data.dto.ApiResult
import com.aloarte.shopwise.domain.ProductBo
import com.aloarte.shopwise.domain.ShopwiseProductsRepository
import javax.inject.Inject

class ShopwiseProductsRepositoryImpl @Inject constructor(private val datasource: ShopwiseProductsDatasource) : ShopwiseProductsRepository {
    override suspend fun fetchProducts(): List<ProductBo> {
        return when (val result = datasource.fetchProducts()){
            is ApiResult.Error -> {
                emptyList()
            }
            is ApiResult.Success -> {
                result.data
            }
        }
    }
}