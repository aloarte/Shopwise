package com.aloarte.shopwise.domain

import com.aloarte.shopwise.domain.cart.ShoppingCart
import com.aloarte.shopwise.domain.cart.ShoppingCartParams
import com.aloarte.shopwise.domain.enums.ProductType
import com.aloarte.shopwise.domain.model.ProductBo
import com.aloarte.shopwise.utils.TestData.DISCOUNTED_TSHIRT_ALT_PRICE
import com.aloarte.shopwise.utils.TestData.DISCOUNTED_TSHIRT_PRICE
import com.aloarte.shopwise.utils.TestData.MUG_ALT_PRICE
import com.aloarte.shopwise.utils.TestData.MUG_PRICE
import com.aloarte.shopwise.utils.TestData.TSHIRT_ALT_PRICE
import com.aloarte.shopwise.utils.TestData.TSHIRT_PRICE
import com.aloarte.shopwise.utils.TestData.VOUCHER_ALT_PRICE
import com.aloarte.shopwise.utils.TestData.VOUCHER_PRICE
import com.aloarte.shopwise.utils.TestData.alternativeMug
import com.aloarte.shopwise.utils.TestData.alternativeTshirt
import com.aloarte.shopwise.utils.TestData.alternativeVoucher
import com.aloarte.shopwise.utils.TestData.mug
import com.aloarte.shopwise.utils.TestData.tshirt
import com.aloarte.shopwise.utils.TestData.voucher
import junit.framework.TestCase.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ShoppingCartTest {

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
    fun `test cart basic operations`() {
        regularCart.addItem(tshirt, 3)
        assertEquals(listOf(Pair(tshirt,3)), regularCart.getCartItems())
        regularCart.resetItem(tshirt)
        assertEquals(listOf(Pair(tshirt,0)), regularCart.getCartItems())
        regularCart.addItem(tshirt, 1)
        assertEquals(listOf(Pair(tshirt,1)), regularCart.getCartItems())
        regularCart.removeItem(tshirt)
        assertEquals(emptyList<Pair<ProductBo,Int>>(), regularCart.getCartItems())
    }

    @Test
    fun `test checkout regular shopping cart no items`() {
        assertEquals(0.0, regularCart.checkout())
    }

    @Test
    fun `test checkout regular shopping cart only vouchers`() {
        regularCart.addItem(voucher, 1)
        assertEquals(5.0, regularCart.checkout())
        assertEquals(5.0, regularCart.getItemsPriceByAndName(ProductType.Voucher))

        regularCart.addItem(voucher, 5)
        //6 item in total, 1 free for each 2, 4 items should be priced = 4x5 = 20
        assertEquals(20.0, regularCart.checkout())
        assertEquals(20.0, regularCart.getItemsPriceByAndName(ProductType.Voucher))

    }

    @Test
    fun `test checkout regular shopping cart only tshirts`() {
        regularCart.addItem(tshirt, 1)
        assertEquals(20.0, regularCart.checkout())
        assertEquals(20.0, regularCart.getItemsPriceByAndName(ProductType.Tshirt))
        regularCart.addItem(tshirt, 2)
        //3 item in total, the price get a discount for every tshirt = 3*19
        assertEquals(57.0, regularCart.checkout())
        assertEquals(57.0, regularCart.getItemsPriceByAndName(ProductType.Tshirt))

    }

    @Test
    fun `test checkout regular shopping cart only mugs`() {
        regularCart.addItem(mug, 6)
        //6 * 7.5 = 45
        assertEquals(45.0, regularCart.checkout())
        assertEquals(45.0, regularCart.getItemsPriceByAndName(ProductType.Mug))

    }

    @Test
    fun `test checkout regular shopping cart mixed items`() {
        regularCart.addItem(mug, 1)
        regularCart.addItem(tshirt, 2)
        regularCart.addItem(voucher, 4)
        regularCart.addItem(tshirt, 3)
        regularCart.addItem(mug, 4)
        regularCart.addItem(voucher, 1)

        //Total 5 tshirts = 5*19 , Total 5 vouchers = 4*5, 5 mugs = 5*7.5
        // 95 + 20 + 37.5 = 152.5
        assertEquals(152.5, regularCart.checkout())
        assertEquals(95.0, regularCart.getItemsPriceByAndName(ProductType.Tshirt))
        assertEquals(20.0, regularCart.getItemsPriceByAndName(ProductType.Voucher))
        assertEquals(37.5, regularCart.getItemsPriceByAndName(ProductType.Mug))

    }

    @Test
    fun `test checkout alternative shopping cart mixed items`() {
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

    @Test
    fun `test checkout without discount  regular shopping cart mixed items`() {
        regularCart.addItem(mug, 1)
        regularCart.addItem(tshirt, 2)
        regularCart.addItem(voucher, 4)
        regularCart.addItem(tshirt, 3)
        regularCart.addItem(mug, 4)
        regularCart.addItem(voucher, 1)

        //Total 5 tshirts = 5*20 , Total 5 vouchers = 5*5, 5 mugs = 5*7.5
        // 100 + 25 + 37.5 = 162.5
        assertEquals(162.5, regularCart.checkoutWithoutDiscount())
        assertEquals(100.0, regularCart.getItemsPriceWithoutDiscountByType(ProductType.Tshirt))
        assertEquals(25.0, regularCart.getItemsPriceWithoutDiscountByType(ProductType.Voucher))
        assertEquals(37.5, regularCart.getItemsPriceWithoutDiscountByType(ProductType.Mug))

    }


    @Test
    fun `test get products number`() {
        regularCart.addItem(mug, 1)
        regularCart.addItem(tshirt, 2)
        regularCart.addItem(voucher, 3)
        Assert.assertEquals(6, regularCart.productsNumber())
    }


    @Test
    fun `test get discounted count by type `() {
        regularCart.addItem(voucher, 6)
        regularCart.addItem(tshirt, 6)
        regularCart.addItem(mug, 6)

        Assert.assertEquals(2, regularCart.getDiscountedCountByType(6, ProductType.Voucher))
        Assert.assertEquals(6, regularCart.getDiscountedCountByType(6, ProductType.Tshirt))
        Assert.assertEquals(0, regularCart.getDiscountedCountByType(6, ProductType.Mug))
    }

    @Test
    fun `test items price without discount by type `() {
        val numberOfItems = 6
        regularCart.addItem(voucher, numberOfItems)
        regularCart.addItem(tshirt, numberOfItems)
        regularCart.addItem(mug, numberOfItems)

        Assert.assertEquals(
            numberOfItems * VOUCHER_PRICE,
            regularCart.getItemsPriceWithoutDiscountByType(ProductType.Voucher),
            0.001
        )
        Assert.assertEquals(
            numberOfItems * TSHIRT_PRICE,
            regularCart.getItemsPriceWithoutDiscountByType(ProductType.Tshirt),
            0.001
        )
        Assert.assertEquals(
            numberOfItems * MUG_PRICE,
            regularCart.getItemsPriceWithoutDiscountByType(ProductType.Mug),
            0.001
        )
    }

}