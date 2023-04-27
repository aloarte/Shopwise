package com.aloarte.shopwise


import com.aloarte.shopwise.domain.ProductBo
import com.aloarte.shopwise.domain.ProductType
import com.aloarte.shopwise.domain.ShoppingCart
import com.aloarte.shopwise.domain.ShoppingCartParams
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ShoppingCartTest {

    private val VOUCHER_PRICE = 5.0
    private val TSHIRT_PRICE = 20.0
    private val DISCOUNTED_TSHIRT_PRICE = 19.0
    private val MUG_PRICE = 7.5

    private val VOUCHER_ALT_PRICE = 6.0
    private val TSHIRT_ALT_PRICE = 25.0
    private val DISCOUNTED_TSHIRT_ALT_PRICE = 20.0
    private val MUG_ALT_PRICE = 5.5



    private val voucher = ProductBo(
        type = ProductType.Voucher,
        code = "VOUCHER",
        name = "Cabify Voucher",
        price = VOUCHER_PRICE
    )

    private val tshirt = ProductBo(
        type = ProductType.Tshirt,
        code = "TSHIRT",
        name = "Cabify T-Shirt",
        price = TSHIRT_PRICE
    )

    private val mug = ProductBo(
        type = ProductType.Mug,
        code = "MUG",
        name = "Cabify Coffee Mug",
        price = MUG_PRICE
    )

    private val alternativeVoucher = ProductBo(
        type = ProductType.Voucher,
        code = "VOUCHER",
        name = "Cabify Voucher",
        price = VOUCHER_ALT_PRICE
    )

    private val alternativeTshirt = ProductBo(
        type = ProductType.Tshirt,
        code = "TSHIRT",
        name = "Cabify T-Shirt",
        price = TSHIRT_ALT_PRICE
    )

    private val alternativeMug = ProductBo(
        type = ProductType.Mug,
        code = "MUG",
        name = "Cabify Coffee Mug",
        price = MUG_ALT_PRICE
    )


    private lateinit var regularCart: ShoppingCart

    private lateinit var alternativeCart: ShoppingCart


    @Before
    fun setup() {
        regularCart = ShoppingCart(
            ShoppingCartParams(
                voucherDefaultPrice = VOUCHER_PRICE,
                tshirtDefaultPrice = TSHIRT_PRICE,
                discountedTshirtPrice = DISCOUNTED_TSHIRT_PRICE,
                mugDefaultPrice = MUG_PRICE,
                voucherDiscountThreshold = 3,
                tshirtDiscountThreshold = 3
            )
        )
        alternativeCart = ShoppingCart(
            ShoppingCartParams(
                voucherDefaultPrice = VOUCHER_ALT_PRICE,
                tshirtDefaultPrice = TSHIRT_ALT_PRICE,
                discountedTshirtPrice = DISCOUNTED_TSHIRT_ALT_PRICE,
                mugDefaultPrice = MUG_ALT_PRICE,
                voucherDiscountThreshold = 4,
                tshirtDiscountThreshold = 2
            )
        )
    }

    @Test
    fun `test regular shopping cart no items`() {
        assertEquals(0.0, regularCart.checkout())
    }

    @Test
    fun  `test regular shopping cart only vouchers`() {
        regularCart.addItem(voucher, 1)
        assertEquals(5.0, regularCart.checkout())
        regularCart.addItem(voucher, 5)
        //6 item in total, 1 free for each 2, 4 items should be priced = 4x5 = 20
        assertEquals(20.0, regularCart.checkout())
    }

    @Test
    fun `test regular shopping cart only tshirts`() {
        regularCart.addItem(tshirt,1)
        assertEquals(20.0, regularCart.checkout())
        regularCart.addItem(tshirt,2)
        //3 item in total, the price get a discount for every tshirt = 3*19
        assertEquals(57.0, regularCart.checkout())

    }

    @Test
    fun `test regular shopping cart only mugs`() {
        regularCart.addItem(mug,6)
        //6 * 7.5 = 45
        assertEquals(45.0, regularCart.checkout())
    }

    @Test
    fun `test regular shopping cart mixed items`() {
        regularCart.addItem(mug, 1)
        regularCart.addItem(tshirt, 2)
        regularCart.addItem(voucher, 4)
        regularCart.addItem(tshirt, 3)
        regularCart.addItem(mug, 4)
        regularCart.addItem(voucher, 1)

        //Total 5 tshirts = 5*19 , Total 5 vouchers = 4*5, 5 mugs = 5*7.5
        // 95 + 20 + 37.5 = 152.5
        assertEquals(152.5, regularCart.checkout())
    }

    @Test
    fun `test alternative shopping cart mixed items`() {
        alternativeCart.addItem(alternativeMug, 1)
        alternativeCart.addItem(alternativeTshirt, 1)
        alternativeCart.addItem(alternativeVoucher, 2)
        //Total 1 tshirt = 1*25 , Total 2 vouchers = 2*6, 1 mug = 5.5
        // 25 + 12 + 5.5 = 40.5
        assertEquals(42.5, alternativeCart.checkout())

        alternativeCart.addItem(alternativeMug, 1)
        alternativeCart.addItem(alternativeTshirt, 2)
        alternativeCart.addItem(alternativeVoucher, 4)
        alternativeCart.addItem(alternativeTshirt, 2)
        alternativeCart.addItem(alternativeMug, 3)
        alternativeCart.addItem(alternativeVoucher, 1)

        //Total 5 tshirts = 5*20 , Total 7 vouchers = 6*6, 5 mugs = 5*5.5
        // 100 + 36 + 27.5 = 163.5
        assertEquals(163.5, alternativeCart.checkout())
    }


}