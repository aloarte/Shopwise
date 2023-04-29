package com.aloarte.shopwise.data.parser

import com.aloarte.shopwise.data.ProductsResponse
import com.aloarte.shopwise.data.dto.ProductDto
import com.aloarte.shopwise.domain.ProductBo
import com.aloarte.shopwise.domain.ProductType
import com.google.gson.Gson
import okhttp3.ResponseBody
import javax.inject.Inject

class DataParser @Inject constructor(private val gson: Gson) {
    fun parseResponse(body: ResponseBody?): ProductsResponse = try {
        body?.string()?.let {
            if (it.isEmpty()) ProductsResponse(null)
            else gson.fromJson(it, ProductsResponse::class.java)

        } ?: ProductsResponse(null)
    } catch (e: Exception) {
        ProductsResponse(null)
    }

    fun transformList(dtoList: List<ProductDto>, descriptions: List<Pair<String, String>>) =
        dtoList.map { dto ->
            transform(dto, descriptions.find { it.first == dto.code }?.second)
        }

    private fun transform(dto: ProductDto, description: String?) = ProductBo(
        type = when (dto.code) {
            "VOUCHER" -> ProductType.Voucher
            "TSHIRT" -> ProductType.Tshirt
            "MUG" -> ProductType.Mug
            else -> ProductType.Mug
        },
        code = dto.code,
        name = dto.name,
        price = dto.price,
        description = description
    )


}