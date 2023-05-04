package com.aloarte.shopwise.data

import com.aloarte.shopwise.data.datasources.ProductsDescriptionsDatasource
import com.aloarte.shopwise.data.datasources.ProductsDescriptionsDatasourceImpl
import com.aloarte.shopwise.utils.TestData.codeItemsList
import com.aloarte.shopwise.utils.TestData.descriptionsPairList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ProductsDescriptionsDatasourceTest {

    private lateinit var datasource: ProductsDescriptionsDatasource

    @Before
    fun setUp() {
        datasource = ProductsDescriptionsDatasourceImpl()
    }

    @Test
    fun `test fetch products success`() {
        val listResult = runBlocking {
            datasource.retrieveDescriptions(codeItemsList)
        }

        Assert.assertEquals(descriptionsPairList, listResult)
    }
}