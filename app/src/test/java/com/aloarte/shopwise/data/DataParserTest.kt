package com.aloarte.shopwise.data

import com.aloarte.shopwise.data.parser.DataParser
import com.aloarte.shopwise.utils.TestData
import com.aloarte.shopwise.utils.TestData.boList
import com.aloarte.shopwise.utils.TestData.boMap
import com.aloarte.shopwise.utils.TestData.descriptionsPairList
import com.aloarte.shopwise.utils.TestData.mugDto1
import com.aloarte.shopwise.utils.TestData.mugDto2
import com.aloarte.shopwise.utils.TestData.productsJson
import com.aloarte.shopwise.utils.TestData.purchaseData
import com.aloarte.shopwise.utils.TestData.rMug
import com.aloarte.shopwise.utils.TestData.rMugDto
import com.aloarte.shopwise.utils.TestData.rTshirt
import com.aloarte.shopwise.utils.TestData.rTshirtDto
import com.aloarte.shopwise.utils.TestData.rVoucher
import com.aloarte.shopwise.utils.TestData.rVoucherDto
import com.aloarte.shopwise.utils.TestData.tshirtDto1
import com.aloarte.shopwise.utils.TestData.tshirtDto2
import com.aloarte.shopwise.utils.TestData.voucherDto1
import com.aloarte.shopwise.utils.TestData.voucherDto2
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

class DataParserTest {

    private var dataParser: DataParser = DataParser(Gson())
    private val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()

    @Test
    fun `test parse response 200 from api real json`() {
        val successfulResponse = Response.success(200, productsJson.toResponseBody(mediaType))

        val responseParsed = dataParser.parseResponse(successfulResponse.body())

        val expected = ProductsResponse(products = listOf(rVoucherDto, rTshirtDto, rMugDto))
        Assert.assertEquals(expected, responseParsed)
    }

    @Test
    fun `test parse response from api 200 not parseable json`() {
        val successfulResponse = Response.success(200, "{bad=,object?}".toResponseBody(mediaType))

        val responseParsed = dataParser.parseResponse(successfulResponse.body())

        Assert.assertEquals(ProductsResponse(), responseParsed)
    }

    @Test
    fun `test parse response local`() {
        val successfulResponse = Response.success(200, "{}".toResponseBody(mediaType))

        val responseParsed = dataParser.parseResponse(successfulResponse.body())

        Assert.assertEquals(ProductsResponse(), responseParsed)
    }

    @Test
    fun `test transform list from api success`() {
        val boList =
            dataParser.transformList(listOf(rVoucherDto, rTshirtDto, rMugDto), descriptionsPairList)

        Assert.assertEquals(listOf(rVoucher, rTshirt, rMug), boList)
    }

    @Test
    fun `test transform list from local success`() {
        val parsedBoList = dataParser.transformList(
            listOf(
                voucherDto1, voucherDto2,
                tshirtDto1, tshirtDto2,
                mugDto1, mugDto2
            )
        )

        Assert.assertEquals(boList, parsedBoList)
    }

    @Test
    fun  `test parse purchase data`(){
        val purchaseDataResponse = dataParser.parsePurchaseData(boMap.toList())

        Assert.assertEquals(purchaseData, purchaseDataResponse)
    }
}