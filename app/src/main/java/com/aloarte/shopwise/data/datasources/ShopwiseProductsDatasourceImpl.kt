package com.aloarte.shopwise.data.datasources

import com.aloarte.shopwise.data.Constants
import com.aloarte.shopwise.data.ProductsApi
import com.aloarte.shopwise.data.dto.ApiResult
import com.aloarte.shopwise.data.parser.DataParser
import com.aloarte.shopwise.domain.ProductBo
import javax.inject.Inject

class ShopwiseProductsDatasourceImpl @Inject constructor(private val api: ProductsApi, private val parser: DataParser) :
    ShopwiseProductsDatasource {
    override suspend fun fetchProducts(): ApiResult<List<ProductBo>> {
        return try {
            val apiResponse = api.fetchProductsJson()
            if (apiResponse.code() == Constants.API_SUCCESS_CODE) {
                val productsResponse = parser.parseResponse(apiResponse.body())
                productsResponse.products?.let { products ->
                    ApiResult.Success(parser.transformList(products))

                } ?: run {
                    ApiResult.Error(
                        errorCode = Constants.API_CALL_EXCEPTION_CODE,
                        errorMessage = Constants.API_CALL_EXCEPTION_MESSAGE
                    )
                }

            } else {
                ApiResult.Error(apiResponse.code(), apiResponse.message())
            }
        } catch (e: Exception) {
            ApiResult.Error(
                errorCode = Constants.API_CALL_EXCEPTION_CODE,
                errorMessage = Constants.API_CALL_EXCEPTION_MESSAGE,
                exception = e
            )
        }
    }
}