package com.aloarte.shopwise.data.datasources

import com.aloarte.shopwise.R
import com.aloarte.shopwise.data.dto.ApiResult
import com.aloarte.shopwise.data.dto.ProductDto
import javax.inject.Inject

class LocalProductsDatasourceImpl @Inject constructor() : ShopwiseProductsDatasource {
    override suspend fun fetchProducts(): ApiResult<List<ProductDto>> = ApiResult.Success(
        listOf(
            ProductDto(
                id = "VOU1",
                name = "Taxi trip",
                code = "VOUCHER",
                price = 5.0,
                description = "Single taxi ride.",
                imageResource = R.drawable.ic_voucher
            ),
            ProductDto(
                id = "VOU2",
                name = "10 bus trips",
                code = "VOUCHER",
                price = 7.5,
                description = "Ten trips on the chosen buses from your city.",
                imageResource = R.drawable.ic_voucher_bus
            ),
            ProductDto(
                id = "VOU3",
                name = "Bike pass",
                code = "VOUCHER",
                price = 20.0,
                description = "A pass to use a bike from your city's services for 24h.",
                imageResource = R.drawable.ic_voucher_bike
            ),
            ProductDto(
                id = "TSH1",
                name = "Basic T-shirt",
                code = "TSHIRT",
                price = 10.0,
                description = "Basic t-shirt.",
                imageResource = R.drawable.ic_tshirt
            ),
            ProductDto(
                id = "TSH2",
                name = "Flowers T-shirt",
                code = "TSHIRT",
                price = 18.0,
                description = "T-shirt with a draw of some flowers.",
                imageResource = R.drawable.ic_tshirt_draw
            ),
            ProductDto(
                id = "TSH3",
                name = "Sports T-shirt",
                code = "TSHIRT",
                price = 16.0,
                description = "Sports t-shirt to support your local favorite team.",
                imageResource = R.drawable.ic_tshirt_sports
            ),
            ProductDto(
                id = "TSH4",
                name = "Long sleeved T-shirt",
                code = "TSHIRT",
                price = 20.0,
                description = "Long sleeved t-shirt.",
                imageResource = R.drawable.ic_tshirt_long_sleeve
            ),
            ProductDto(
                id = "TSH5",
                name = "Informal T-shirt",
                code = "TSHIRT",
                price = 20.0,
                description = "An informal t-shirt.",
                imageResource = R.drawable.ic_tshirt_informal
            ),
            ProductDto(
                id = "MUG1",
                name = "Mug",
                code = "MUG",
                price = 4.0,
                description = "An informal t-shirt.",
                imageResource = R.drawable.ic_mug
            ),
            ProductDto(
                id = "MUG2",
                name = "Styled mug",
                code = "MUG",
                price = 8.0,
                description = "An styled porcelain mug.",
                imageResource = R.drawable.ic_mug_styled
            )
        )
    )

}