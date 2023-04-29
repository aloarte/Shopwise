package com.aloarte.shopwise.data.datasources

import javax.inject.Inject


class ProductsDescriptionsDatasourceImpl @Inject constructor(): ProductsDescriptionsDatasource {
    override fun retrieveDescriptions(productCode: List<String>): List<Pair<String, String>> {
        return listOf(
            Pair(
                "VOUCHER",
                "A voucher for a Cabify trip.\nGet a 3x2 discount offer to save some money in your future trips."
            ),
            Pair(
                "TSHIRT",
                "A Cabify T-shirt with their fantastic design.\nBuy 3 or more to enjoy of a price discount on all of the selected units."
            ),
            Pair("MUG", "A simple mug to start you day with high energy.")
        )
    }


}