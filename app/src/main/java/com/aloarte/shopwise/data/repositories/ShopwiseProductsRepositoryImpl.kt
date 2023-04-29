package com.aloarte.shopwise.data.repositories

import com.aloarte.shopwise.data.datasources.ProductsDescriptionsDatasource
import com.aloarte.shopwise.data.datasources.ShopwiseProductsDatasource
import com.aloarte.shopwise.data.dto.ApiResult
import com.aloarte.shopwise.data.parser.DataParser
import com.aloarte.shopwise.domain.ProductBo
import com.aloarte.shopwise.domain.ShopwiseProductsRepository
import javax.inject.Inject

class ShopwiseProductsRepositoryImpl @Inject constructor(
    private val productDatasource: ShopwiseProductsDatasource,
    private val descriptionsDatasource: ProductsDescriptionsDatasource,
    private val parser: DataParser
) : ShopwiseProductsRepository {
    override suspend fun fetchProducts(): List<ProductBo> {
        return when (val result = productDatasource.fetchProducts()) {
            is ApiResult.Error -> {
                emptyList()
            }

            is ApiResult.Success -> {
                val apiResult = result.data
                val productsCodeList = mutableListOf<String>()
                apiResult.forEach { productsCodeList.add(it.code) }
                val descriptions = descriptionsDatasource.retrieveDescriptions(productsCodeList)
                parser.transformList(apiResult, descriptions)
            }
        }
    }
}