package com.aloarte.shopwise.utils

import com.aloarte.shopwise.data.dto.ProductDto
import com.aloarte.shopwise.domain.enums.CardPaymentNetworkType
import com.aloarte.shopwise.domain.enums.CardType
import com.aloarte.shopwise.domain.enums.ProductType
import com.aloarte.shopwise.domain.model.CardBo
import com.aloarte.shopwise.domain.model.ProductBo
import com.aloarte.shopwise.domain.model.PurchaseDataItem

object TestData {

    const val VOUCHER_PRICE = 5.0
    const val VOUCHER_CODE = "VOUCHER"

    const val VOUCHER_DESCR = "A voucher for a Cabify trip.\n" +
            "Get a 3x2 discount offer to save some money in your future trips."
    const val TSHIRT_PRICE = 20.0
    const val TSHIRT_CODE = "TSHIRT"
    const val TSHIRT_NAME = "Cabify T-Shirt"
    const val TSHIRT_DESCR = "A Cabify T-shirt with their fantastic design.\n" +
            "Buy 3 or more to enjoy of a price discount on all of the selected units."
    const val DISCOUNTED_TSHIRT_PRICE = 19.0
    const val MUG_PRICE = 7.5
    const val MUG_CODE = "MUG"
    const val MUG_DESCR = "A simple mug to start you day with high energy."
    const val VOUCHER_ALT_PRICE = 6.0
    const val TSHIRT_ALT_PRICE = 25.0
    const val DISCOUNTED_TSHIRT_ALT_PRICE = 20.0
    const val MUG_ALT_PRICE = 5.5

    val voucherDto = ProductDto(
        code = "VOUCHER",
        name = "Cabify Voucher",
        price = VOUCHER_PRICE
    )

    val tshirtDto = ProductDto(
        code = "TSHIRT",
        name = TSHIRT_NAME,
        price = TSHIRT_PRICE
    )

    val mugDto = ProductDto(
        code = "MUG",
        name = "Cabify Coffee Mug",
        price = MUG_PRICE
    )

    val voucher = ProductBo(
        type = ProductType.Voucher,
        code = "VOUCHER",
        name = "Cabify Voucher",
        price = VOUCHER_PRICE,
        description = VOUCHER_DESCR
    )

    val tshirt = ProductBo(
        type = ProductType.Tshirt,
        code = "TSHIRT",
        name = "Cabify T-Shirt",
        price = TSHIRT_PRICE,
        description = TSHIRT_DESCR

    )

    val mug = ProductBo(
        type = ProductType.Mug,
        code = "MUG",
        name = "Cabify Coffee Mug",
        price = MUG_PRICE,
        description = MUG_DESCR
    )

    val alternativeVoucher = ProductBo(
        type = ProductType.Voucher,
        code = "VOUCHER",
        name = "Cabify Voucher",
        price = VOUCHER_ALT_PRICE
    )

    val alternativeTshirt = ProductBo(
        type = ProductType.Tshirt,
        code = "TSHIRT",
        name = "Cabify T-Shirt",
        price = TSHIRT_ALT_PRICE
    )

    val alternativeMug = ProductBo(
        type = ProductType.Mug,
        code = "MUG",
        name = "Cabify Coffee Mug",
        price = MUG_ALT_PRICE
    )

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

    val productsBoList = listOf(voucher, tshirt, mug)

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