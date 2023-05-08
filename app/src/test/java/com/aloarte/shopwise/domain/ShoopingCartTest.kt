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
import com.aloarte.shopwise.utils.TestData.VOUCHER_1_PRICE
import com.aloarte.shopwise.utils.TestData.VOUCHER_2_PRICE
import com.aloarte.shopwise.utils.TestData.VOUCHER_ALT_PRICE
import com.aloarte.shopwise.utils.TestData.VOUCHER_PRICE
import com.aloarte.shopwise.utils.TestData.alternativeMug
import com.aloarte.shopwise.utils.TestData.alternativeTshirt
import com.aloarte.shopwise.utils.TestData.alternativeVoucher
import com.aloarte.shopwise.utils.TestData.mug1
import com.aloarte.shopwise.utils.TestData.tshirt1
import com.aloarte.shopwise.utils.TestData.tshirt2
import com.aloarte.shopwise.utils.TestData.voucher1
import com.aloarte.shopwise.utils.TestData.voucher2
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
        regularCart.addItem(tshirt1, 3)
        assertEquals(listOf(Pair(tshirt1, 3)), regularCart.getCartItems())
        regularCart.resetItem(tshirt1)
        assertEquals(listOf(Pair(tshirt1, 0)), regularCart.getCartItems())
        regularCart.addItem(tshirt1, 1)
        assertEquals(listOf(Pair(tshirt1, 1)), regularCart.getCartItems())
        regularCart.removeItem(tshirt1)
        assertEquals(emptyList<Pair<ProductBo, Int>>(), regularCart.getCartItems())
    }

    @Test
    fun `test checkout regular shopping cart no items`() {
        assertEquals(0.0, regularCart.checkout())
    }

    @Test
    fun `test checkout regular shopping cart only vouchers`() {
        regularCart.addItem(voucher1, 1)
        assertEquals(VOUCHER_1_PRICE, regularCart.checkout())
        assertEquals(VOUCHER_1_PRICE, regularCart.getItemsPrice(ProductType.Voucher))

        regularCart.addItem(voucher1, 5)
        assertEquals(VOUCHER_1_PRICE * 4, regularCart.checkout())
        assertEquals(VOUCHER_1_PRICE * 4, regularCart.getItemsPrice(ProductType.Voucher))

        regularCart.addItem(voucher2, 3)
        assertEquals(VOUCHER_1_PRICE * 4 + VOUCHER_2_PRICE * 2, regularCart.checkout())
        assertEquals(
            VOUCHER_1_PRICE * 4 + VOUCHER_2_PRICE * 2,
            regularCart.getItemsPrice(ProductType.Voucher)
        )
    }

    @Test
    fun `test checkout regular shopping cart only tshirts`() {
        regularCart.addItem(tshirt1, 1)
        assertEquals(tshirt1.price, regularCart.checkout())
        assertEquals(tshirt1.price, regularCart.getItemsPrice(ProductType.Tshirt))

        val tshirt1price = (tshirt1.price - DISCOUNTED_TSHIRT_PRICE) * 3
        regularCart.addItem(tshirt1, 2)
        assertEquals(tshirt1price, regularCart.checkout())
        assertEquals(tshirt1price, regularCart.getItemsPrice(ProductType.Tshirt))

        val tshirt2price = (tshirt2.price - DISCOUNTED_TSHIRT_PRICE) * 3
        regularCart.addItem(tshirt2, 3)
        assertEquals(tshirt2price + tshirt1price, regularCart.checkout())
        assertEquals(
            tshirt2price + tshirt1price,
            regularCart.getItemsPrice(ProductType.Tshirt)
        )
    }

    @Test
    fun `test checkout regular shopping cart only mugs`() {
        regularCart.addItem(mug1, 6)
        assertEquals(mug1.price * 6, regularCart.checkout())
        assertEquals(mug1.price * 6, regularCart.getItemsPrice(ProductType.Mug))
    }

    @Test
    fun `test checkout regular shopping cart mixed items`() {
        regularCart.addItem(voucher1, 4)
        regularCart.addItem(tshirt1, 2)
        regularCart.addItem(mug1, 1)

        val vouchersPrice = voucher1.price * 3
        val tshirtsPrice = tshirt1.price * 2
        val mugsPrice = mug1.price
        assertEquals(vouchersPrice, regularCart.getItemsPrice(ProductType.Voucher))
        assertEquals(tshirtsPrice, regularCart.getItemsPrice(ProductType.Tshirt))
        assertEquals(mugsPrice, regularCart.getItemsPrice(ProductType.Mug))
        assertEquals(tshirtsPrice + mugsPrice + vouchersPrice, regularCart.checkout())
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

        //Total 5 tshirts = 5*(25-2) , Total 7 vouchers = 6*6, 5 mugs = 5*5.5
        // 90 + 36 + 27.5 = 163.5
        assertEquals(178.5, alternativeCart.checkout())
    }

    @Test
    fun `test checkout without discount regular shopping cart mixed items`() {
        regularCart.addItem(voucher1, 4)
        regularCart.addItem(tshirt1, 2)
        regularCart.addItem(mug1, 1)

        val vouchersPrice = voucher1.price * 4
        val tshirtsPrice = tshirt1.price * 2
        val mugsPrice = mug1.price
        assertEquals(vouchersPrice, regularCart.getItemsPrice(ProductType.Voucher, false))
        assertEquals(tshirtsPrice, regularCart.getItemsPrice(ProductType.Tshirt,false))
        assertEquals(mugsPrice, regularCart.getItemsPrice(ProductType.Mug,false))
        assertEquals(
            tshirtsPrice + mugsPrice + vouchersPrice,
            regularCart.checkout(applyDiscounts = false)
        )
    }

    @Test
    fun `test get products number`() {
        regularCart.addItem(mug1, 1)
        regularCart.addItem(tshirt1, 2)
        regularCart.addItem(voucher1, 3)
        Assert.assertEquals(6, regularCart.productsNumber())
    }

    @Test
    fun `test are products discounted`() {
        Assert.assertTrue(regularCart.areProductsDiscounted(3, ProductType.Voucher))
        Assert.assertTrue(regularCart.areProductsDiscounted(3, ProductType.Tshirt))
        Assert.assertFalse(regularCart.areProductsDiscounted(3, ProductType.Mug))
    }

    @Test
    fun `test items price with discount by type and name`() {
        regularCart.addItem(voucher1, 2)
        regularCart.addItem(voucher2, 4)

        val voucher1Price = 2 * voucher1.price
        val voucher2Price = 3 * voucher2.price

        Assert.assertEquals(
            voucher1Price,
            regularCart.getDiscountedItemsPrice(ProductType.Voucher,voucher1.name),
            0.001
        )
        Assert.assertEquals(
            voucher2Price,
            regularCart.getDiscountedItemsPrice(ProductType.Voucher,voucher2.name),
            0.001
        )
        assertEquals(voucher1Price+voucher2Price, regularCart.getItemsPrice(ProductType.Voucher))
    }

    @Test
    fun `test items price without discount by type and name`() {
        regularCart.addItem(voucher1, 2)
        regularCart.addItem(voucher2, 4)

        val voucher1Price = 2 * voucher1.price
        val voucher2Price = 4 * voucher2.price

        Assert.assertEquals(
            voucher1Price,
            regularCart.getNotDiscountedItemsPrice(ProductType.Voucher,voucher1.name),
            0.001
        )
        Assert.assertEquals(
            voucher2Price,
            regularCart.getNotDiscountedItemsPrice(ProductType.Voucher,voucher2.name),
            0.001
        )
        assertEquals(voucher1Price+voucher2Price, regularCart.getItemsPrice(ProductType.Voucher,false))
    }

}