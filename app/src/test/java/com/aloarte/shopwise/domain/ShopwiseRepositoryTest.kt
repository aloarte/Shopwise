package com.aloarte.shopwise.domain

import com.aloarte.shopwise.data.datasources.ProductsDescriptionsDatasource
import com.aloarte.shopwise.data.datasources.ShopwiseProductsDatasource
import com.aloarte.shopwise.data.dto.ApiResult
import com.aloarte.shopwise.data.parser.DataParser
import com.aloarte.shopwise.data.repositories.ShopwiseProductsRepositoryImpl
import com.aloarte.shopwise.domain.model.ProductBo
import com.aloarte.shopwise.domain.repositories.ShopwiseProductsRepository
import com.aloarte.shopwise.utils.TestData.codeItemsList
import com.aloarte.shopwise.utils.TestData.descriptionsPairList
import com.aloarte.shopwise.utils.TestData.mug
import com.aloarte.shopwise.utils.TestData.mugDto
import com.aloarte.shopwise.utils.TestData.tshirt
import com.aloarte.shopwise.utils.TestData.tshirtDto
import com.aloarte.shopwise.utils.TestData.voucher
import com.aloarte.shopwise.utils.TestData.voucherDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ShopwiseRepositoryTest {

    @MockK
    private lateinit var productsDatasource: ShopwiseProductsDatasource

    @MockK
    private lateinit var descriptionsDatasource: ProductsDescriptionsDatasource

    @MockK
    private lateinit var parser: DataParser

    private lateinit var repository: ShopwiseProductsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = ShopwiseProductsRepositoryImpl(productsDatasource,descriptionsDatasource, parser)
    }

    @Test
    fun `test fetch products success`() {
        val dtoProductList = listOf(voucherDto, tshirtDto, mugDto)
        val boProductList = listOf(voucher, tshirt, mug)
        coEvery { productsDatasource.fetchProducts() } returns ApiResult.Success(dtoProductList)
        every { descriptionsDatasource.retrieveDescriptions(codeItemsList) } returns descriptionsPairList
        every { parser.transformList(dtoProductList,descriptionsPairList) } returns boProductList

        val productListResponse = runBlocking { repository.fetchProducts() }

        coVerify { productsDatasource.fetchProducts() }
        verify { descriptionsDatasource.retrieveDescriptions(codeItemsList) }
        verify { parser.transformList(dtoProductList,descriptionsPairList) }

        Assert.assertEquals(boProductList, productListResponse)
    }

    @Test
    fun `test fetch products error`() {
        coEvery { productsDatasource.fetchProducts() } returns ApiResult.Error(
            -1,
            "Error retrieving stats contents"
        )

        val productListResponse = runBlocking { repository.fetchProducts() }

        coVerify { productsDatasource.fetchProducts() }
        Assert.assertEquals(emptyList<ProductBo>(),productListResponse)
    }
}