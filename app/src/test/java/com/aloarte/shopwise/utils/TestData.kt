package com.aloarte.shopwise.utils

import com.aloarte.shopwise.data.dto.ProductDto
import com.aloarte.shopwise.domain.enums.CardPaymentNetworkType
import com.aloarte.shopwise.domain.enums.CardType
import com.aloarte.shopwise.domain.enums.ProductType
import com.aloarte.shopwise.domain.model.CardBo
import com.aloarte.shopwise.domain.model.ProductBo

object TestData {

    private const val VOUCHER_CODE = "VOUCHER"
    private const val VOUCHER_1_ID = "VOU1"
    private const val VOUCHER_1_NAME = "Voucher 1 name"
    const val VOUCHER_1_PRICE = 5.0
    private const val VOUCHER_1_DESCR = "Voucher 1 descr"
    private const val VOUCHER_1_IMAGE = 1
    private const val VOUCHER_2_ID = "VOU2"
    private const val VOUCHER_2_NAME = "Voucher 2 name"
    const val VOUCHER_2_PRICE = 7.0
    private const val VOUCHER_2_DESCR = "Voucher 2 descr"
    private const val VOUCHER_2_IMAGE = 2
    const val TSHIRT_CODE = "TSHIRT"
    private const val TSHIRT_1_ID = "TSH1"
    private const val TSHIRT_1_NAME = "Tshirt 1 name"
    private const val TSHIRT_1_PRICE = 10.0
    private const val TSHIRT_1_DESCR = "Tshirt 1 descr"
    private const val TSHIRT_1_IMAGE = 1
    private const val TSHIRT_2_ID = "TSH2"
    private const val TSHIRT_2_NAME = "Tshirt 2 name"
    private const val TSHIRT_2_PRICE = 20.0
    private const val TSHIRT_2_DESCR = "Tshirt 2 descr"
    private const val TSHIRT_2_IMAGE = 2
    private const val MUG_CODE = "MUG"
    private const val MUG_1_ID = "MUG1"
    private const val MUG_1_NAME = "Mug 1 name"
    private const val MUG_1_PRICE = 3.0
    private const val MUG_1_DESCR = "Mug 1 descr"
    private const val MUG_1_IMAGE = 1
    private const val MUG_2_ID = "MUG2"
    private const val MUG_2_NAME = "Mug 1 name"
    private const val MUG_2_PRICE = 5.0
    private const val MUG_2_DESCR = "Mug 2 descr"
    private const val MUG_2_IMAGE = 2


    const val VOUCHER_ALT_PRICE = 6.0
    const val TSHIRT_ALT_PRICE = 25.0
    const val DISCOUNTED_TSHIRT_ALT_PRICE = 20.0
    const val MUG_ALT_PRICE = 5.5

    val voucherDto1 = ProductDto(
        id = VOUCHER_1_ID,
        code = VOUCHER_CODE,
        name = VOUCHER_1_NAME,
        price = VOUCHER_1_PRICE,
        description = VOUCHER_1_DESCR,
        imageResource = VOUCHER_1_IMAGE
    )

    val voucherDto2 = ProductDto(
        id = VOUCHER_2_ID,
        code = VOUCHER_CODE,
        name = VOUCHER_2_NAME,
        price = VOUCHER_2_PRICE,
        description = VOUCHER_2_DESCR,
        imageResource = VOUCHER_2_IMAGE
    )

    val tshirtDto1 = ProductDto(
        id = TSHIRT_1_ID,
        code = TSHIRT_CODE,
        name = TSHIRT_1_NAME,
        price = TSHIRT_1_PRICE,
        description = TSHIRT_1_DESCR,
        imageResource = TSHIRT_1_IMAGE
    )

    val tshirtDto2 = ProductDto(
        id = TSHIRT_2_ID,
        code = TSHIRT_CODE,
        name = TSHIRT_2_NAME,
        price = TSHIRT_2_PRICE,
        description = TSHIRT_2_DESCR,
        imageResource = TSHIRT_2_IMAGE
    )

    val mugDto1 = ProductDto(
        id = MUG_1_ID,
        code = MUG_CODE,
        name = MUG_1_NAME,
        price = MUG_1_PRICE,
        description = MUG_1_DESCR,
        imageResource = MUG_1_IMAGE
    )

    val mugDto2 = ProductDto(
        id = MUG_2_ID,
        code = MUG_CODE,
        name = MUG_2_NAME,
        price = MUG_2_PRICE,
        description = MUG_2_DESCR,
        imageResource = MUG_2_IMAGE
    )

    val voucher1 = ProductBo(
        type = ProductType.Voucher,
        id = VOUCHER_1_ID,
        code = VOUCHER_CODE,
        name = VOUCHER_1_NAME,
        price = VOUCHER_1_PRICE,
        description = VOUCHER_1_DESCR,
        imageResource = VOUCHER_1_IMAGE
    )

    val voucher2 = ProductBo(
        type = ProductType.Voucher,
        id = VOUCHER_2_ID,
        code = VOUCHER_CODE,
        name = VOUCHER_2_NAME,
        price = VOUCHER_2_PRICE,
        description = VOUCHER_2_DESCR,
        imageResource = VOUCHER_2_IMAGE
    )

    val tshirt1 = ProductBo(
        type = ProductType.Tshirt,
        id = TSHIRT_1_ID,
        code = TSHIRT_CODE,
        name = TSHIRT_1_NAME,
        price = TSHIRT_1_PRICE,
        description = TSHIRT_1_DESCR,
        imageResource = TSHIRT_1_IMAGE

    )

    val tshirt2 = ProductBo(
        type = ProductType.Tshirt,
        id = TSHIRT_2_ID,
        code = TSHIRT_CODE,
        name = TSHIRT_2_NAME,
        price = TSHIRT_2_PRICE,
        description = TSHIRT_2_DESCR,
        imageResource = TSHIRT_2_IMAGE

    )

    val mug1 = ProductBo(
        type = ProductType.Mug,
        id = MUG_1_ID,
        code = MUG_CODE,
        name = MUG_1_NAME,
        price = MUG_1_PRICE,
        description = MUG_1_DESCR,
        imageResource = MUG_1_IMAGE
    )

    val mug2 = ProductBo(
        type = ProductType.Mug,
        id = MUG_2_ID,
        code = MUG_CODE,
        name = MUG_2_NAME,
        price = MUG_2_PRICE,
        description = MUG_2_DESCR,
        imageResource = MUG_2_IMAGE
    )


    const val VOUCHER_PRICE = 5.0

    const val VOUCHER_DESCR = "A voucher for a Cabify trip.\n" +
            "Get a 3x2 discount offer to save some money in your future trips."
    const val TSHIRT_PRICE = 20.0
    const val TSHIRT_NAME = "Cabify T-Shirt"
    const val TSHIRT_DESCR = "A Cabify T-shirt with their fantastic design.\n" +
            "Buy 3 or more to enjoy of a price discount on all of the selected units."
    const val DISCOUNTED_TSHIRT_PRICE = 1.0
    const val MUG_PRICE = 7.5
    const val MUG_DESCR = "A simple mug to start you day with high energy."

    val rVoucherDto = ProductDto(
        code = "VOUCHER",
        name = "Cabify Voucher",
        price = VOUCHER_PRICE
    )

    val rTshirtDto = ProductDto(
        code = "TSHIRT",
        name = TSHIRT_NAME,
        price = TSHIRT_PRICE
    )

    val rMugDto = ProductDto(
        code = "MUG",
        name = "Cabify Coffee Mug",
        price = MUG_PRICE
    )

    val rVoucher = ProductBo(
        type = ProductType.Voucher,
        code = "VOUCHER",
        name = "Cabify Voucher",
        price = VOUCHER_PRICE,
        description = VOUCHER_DESCR
    )

    val rTshirt = ProductBo(
        type = ProductType.Tshirt,
        code = "TSHIRT",
        name = "Cabify T-Shirt",
        price = TSHIRT_PRICE,
        description = TSHIRT_DESCR

    )

    val rMug = ProductBo(
        type = ProductType.Mug,
        code = "MUG",
        name = "Cabify Coffee Mug",
        price = MUG_PRICE,
        description = MUG_DESCR
    )
//
//    val alternativeVoucher = ProductBo(
//        type = ProductType.Voucher,
//        code = "VOUCHER",
//        name = "Cabify Voucher",
//        price = VOUCHER_ALT_PRICE
//    )
//
//    val alternativeTshirt = ProductBo(
//        type = ProductType.Tshirt,
//        code = "TSHIRT",
//        name = "Cabify T-Shirt",
//        price = TSHIRT_ALT_PRICE
//    )
//
//    val alternativeMug = ProductBo(
//        type = ProductType.Mug,
//        code = "MUG",
//        name = "Cabify Coffee Mug",
//        price = MUG_ALT_PRICE
//    )

    const val productsJson = "{\n" +
            "  \"products\": [\n" +
            "    {\n" +
            "      \"code\": \"VOUCHER\",\n" +
            "      \"name\": \"Cabify Voucher\",\n" +
            "      \"price\": 5\n" +
            "    },\n" +
            "    {\n" +
            "      \"code\": \"TSHIRT\",\n" +
            "      \"name\": \"Cabify T-Shirt\",\n" +
            "      \"price\": 20\n" +
            "    },\n" +
            "    {\n" +
            "      \"code\": \"MUG\",\n" +
            "      \"name\": \"Cabify Coffee Mug\",\n" +
            "      \"price\": 7.5\n" +
            "    }\n" +
            "  ]\n" +
            "}"

    val codeItemsList = listOf(
        VOUCHER_CODE,
        TSHIRT_CODE,
        MUG_CODE
    )

    val descriptionsPairList = listOf(
        Pair(VOUCHER_CODE, VOUCHER_DESCR),
        Pair(TSHIRT_CODE, TSHIRT_DESCR),
        Pair(MUG_CODE, MUG_DESCR)
    )

    val remoteProductsBoList = listOf(rVoucher, rTshirt, rMug)

    val localProductsBoList = listOf(voucher1, tshirt1, mug1)

    val cardsBoList = listOf(
        CardBo(
            type = CardType.Credit,
            paymentNetworkType = CardPaymentNetworkType.Mastercard,
            cardNumber = "3678 2014 0205 7840",
            ownerName = "J. Doe",
            ccv = "502",
            expirationDate = "03/30"
        ),
        CardBo(
            type = CardType.Debit,
            paymentNetworkType = CardPaymentNetworkType.Visa,
            cardNumber = "5800 1020 9845 3614",
            ownerName = "J. Doe",
            ccv = "184",
            expirationDate = "11/32"
        )
    )
}