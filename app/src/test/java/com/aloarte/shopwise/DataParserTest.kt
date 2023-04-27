package com.aloarte.shopwise

import com.aloarte.shopwise.data.ProductsResponse
import com.aloarte.shopwise.data.parser.DataParser
import com.aloarte.shopwise.utils.TestData.mug
import com.aloarte.shopwise.utils.TestData.mugDto
import com.aloarte.shopwise.utils.TestData.productsJson
import com.aloarte.shopwise.utils.TestData.tshirt
import com.aloarte.shopwise.utils.TestData.tshirtDto
import com.aloarte.shopwise.utils.TestData.voucher
import com.aloarte.shopwise.utils.TestData.voucherDto
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
    fun `test parse response 200 real json`() {
        val successfulResponse = Response.success(200, productsJson.toResponseBody(mediaType))

        val responseParsed = dataParser.parseResponse(successfulResponse.body())

        val expected = ProductsResponse(products = listOf(voucherDto, tshirtDto, mugDto))
        Assert.assertEquals(expected, responseParsed)
    }

    @Test
    fun `test parse response 200 empty json`() {
        val successfulResponse = Response.success(200, "{}".toResponseBody(mediaType))

        val responseParsed = dataParser.parseResponse(successfulResponse.body())

        Assert.assertEquals(ProductsResponse(), responseParsed)
    }

    @Test
    fun `test parse response 200 unparseable json`() {
        val successfulResponse = Response.success(200, "{bad=,object?}".toResponseBody(mediaType))

        val responseParsed = dataParser.parseResponse(successfulResponse.body())

        Assert.assertEquals(ProductsResponse(), responseParsed)
    }

    @Test
    fun `test transform list success`() {
        val boList = dataParser.transformList(listOf(voucherDto, tshirtDto, mugDto))

        Assert.assertEquals(listOf(voucher, tshirt, mug), boList)
    }


}