package com.aloarte.shopwise.data.datasources


interface ProductsDescriptionsDatasource {

    fun retrieveDescriptions(productCode:List<String>): List<Pair<String,String>>

}