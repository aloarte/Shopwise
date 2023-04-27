package com.aloarte.shopwise

import com.aloarte.shopwise.data.datasources.ShopwiseProductsDatasource
import com.aloarte.shopwise.data.dto.ApiResult
import com.aloarte.shopwise.data.repositories.ShopwiseProductsRepositoryImpl
import com.aloarte.shopwise.domain.ProductBo
import com.aloarte.shopwise.domain.ShopwiseProductsRepository
import com.aloarte.shopwise.utils.TestData.mug
import com.aloarte.shopwise.utils.TestData.tshirt
import com.aloarte.shopwise.utils.TestData.voucher
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ShopwiseRepositoryTest {

    @MockK
    private lateinit var datasource: ShopwiseProductsDatasource

    private lateinit var repository: ShopwiseProductsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = ShopwiseProductsRepositoryImpl(datasource)
    }

    @Test
    fun `test fetch products success`() {
        val productList = listOf(voucher, tshirt, mug)
        coEvery { datasource.fetchProducts() } returns ApiResult.Success(productList)

        val productListResponse = runBlocking { repository.fetchProducts() }

        coVerify { datasource.fetchProducts() }
        Assert.assertEquals(productList, productListResponse)
    }

    @Test
    fun `test fetch products error`() {
        coEvery { datasource.fetchProducts() } returns ApiResult.Error(
            -1,
            "Error retrieving stats contents"
        )

        val productListResponse = runBlocking { repository.fetchProducts() }

        coVerify { datasource.fetchProducts() }
        Assert.assertEquals(emptyList<ProductBo>(),productListResponse)
    }
}