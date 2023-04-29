package com.aloarte.shopwise.data.datasources


interface ProductsDescriptionsDatasource {

    suspend fun retrieveDescriptions(productCode:List<String>): List<Pair<String,String>>

}