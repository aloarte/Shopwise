package com.aloarte.shopwise

import com.aloarte.shopwise.domain.ProductBo
import com.aloarte.shopwise.domain.ProductType

object TestData {


    const val VOUCHER_PRICE = 5.0
    const val TSHIRT_PRICE = 20.0
    const val DISCOUNTED_TSHIRT_PRICE = 19.0
    const val MUG_PRICE = 7.5
    const val VOUCHER_ALT_PRICE = 6.0
    const val TSHIRT_ALT_PRICE = 25.0
    const val DISCOUNTED_TSHIRT_ALT_PRICE = 20.0
    const val MUG_ALT_PRICE = 5.5

    val voucher = ProductBo(
        type = ProductType.Voucher,
        code = "VOUCHER",
        name = "Cabify Voucher",
        price = VOUCHER_PRICE
    )

    val tshirt = ProductBo(
        type = ProductType.Tshirt,
        code = "TSHIRT",
        name = "Cabify T-Shirt",
        price = TSHIRT_PRICE
    )

    val mug = ProductBo(
        type = ProductType.Mug,
        code = "MUG",
        name = "Cabify Coffee Mug",
        price = MUG_PRICE
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

}