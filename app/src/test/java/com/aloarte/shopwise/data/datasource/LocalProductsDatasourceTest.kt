package com.aloarte.shopwise.data.datasource

import com.aloarte.shopwise.data.datasources.LocalProductsDatasourceImpl
import com.aloarte.shopwise.data.datasources.ShopwiseProductsDatasource
import com.aloarte.shopwise.data.dto.ApiResult
import com.aloarte.shopwise.utils.CoroutinesTestRule
import io.mockk.MockKAnnotations
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LocalProductsDatasourceTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var datasource: ShopwiseProductsDatasource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        datasource = LocalProductsDatasourceImpl()
    }

    @Test
    fun `test fetch products success`() = coroutinesTestRule.runBlockingTest {
        val result = datasource.fetchProducts()

        Assert.assertTrue(result is ApiResult.Success)
        val data = (result as ApiResult.Success).data
        Assert.assertEquals(10, data.size)
    }
}