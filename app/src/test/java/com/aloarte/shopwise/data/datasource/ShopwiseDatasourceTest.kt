package com.aloarte.shopwise.data.datasource

import com.aloarte.shopwise.data.ProductsApi
import com.aloarte.shopwise.data.datasources.ShopwiseProductsDatasource
import com.aloarte.shopwise.data.datasources.ShopwiseProductsDatasourceImpl
import com.aloarte.shopwise.data.dto.ApiResult
import com.aloarte.shopwise.data.parser.DataParser
import com.aloarte.shopwise.domain.model.ProductBo
import com.aloarte.shopwise.utils.CoroutinesTestRule
import com.aloarte.shopwise.utils.TestData.rMugDto
import com.aloarte.shopwise.utils.TestData.productsJson
import com.aloarte.shopwise.utils.TestData.rTshirtDto
import com.aloarte.shopwise.utils.TestData.rVoucherDto
import com.aloarte.shopwise.utils.Utils.buildResponse
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShopwiseDatasourceTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    private lateinit var api: ProductsApi

    private val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()

    private lateinit var datasource: ShopwiseProductsDatasource

    private var dataParser: DataParser = DataParser(Gson())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        datasource = ShopwiseProductsDatasourceImpl(api, dataParser)
    }

    @Test
    fun `test fetch products success`() = coroutinesTestRule.runBlockingTest {
        coEvery { api.fetchProductsJson() } returns mediaType.buildResponse(
            resultCode = 200,
            json = productsJson
        )

        val listResult = runBlocking { datasource.fetchProducts() }

        val expected = ApiResult.Success(listOf(rVoucherDto, rTshirtDto, rMugDto))

        coVerify { api.fetchProductsJson() }
        Assert.assertEquals(expected, listResult)
    }

    @Test
    fun `test fetch products error`() = coroutinesTestRule.runBlockingTest {
        coEvery { api.fetchProductsJson() } returns mediaType.buildResponse(resultCode = 404)

        val listResult = runBlocking { datasource.fetchProducts() }

        val expected =
            ApiResult.Error<List<ProductBo>>(errorCode = 404, errorMessage = "Response.error()")
        coVerify { api.fetchProductsJson() }
        Assert.assertEquals(expected, listResult)
    }
}