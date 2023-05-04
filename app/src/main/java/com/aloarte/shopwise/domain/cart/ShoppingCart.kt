package com.aloarte.shopwise.domain.cart

import com.aloarte.shopwise.domain.enums.ProductType
import com.aloarte.shopwise.domain.model.ProductBo

class ShoppingCart(private val cartParams: ShoppingCartParams) {

    private val products: MutableMap<ProductBo, Int> = mutableMapOf()

    /*
     * Add a product to the map. Update if already exist. Add the quantity of items
     */
    fun addItem(product: ProductBo, quantity: Int) {
        products[product] = products.getOrDefault(product, 0) + quantity
    }

    /*
     * Reset a product quantity in the map if exist.
     */
    fun resetItem(product: ProductBo) {
        if (products.containsKey(product)) {
            products[product] = 0
        }
    }

    /*
     * Remove a product in the map if exist.
     */
    fun removeItem(product: ProductBo) {
        if (products.containsKey(product)) {
            products.remove(product)
        }
    }

    fun clearCart() {
        products.clear()
    }

    /*
     * Return the list of Pair<ProductBo, Int) from the products map
     */
    fun getCartItems() = products.toList()

    /*
     * Calculate the checkout of the cart applying the discounts
     */
    fun checkout(): Double {
        val vouchersPrice = getItemsPriceByType(ProductType.Voucher)
        val tshirtsPrice = getItemsPriceByType(ProductType.Tshirt)
        val generalNonDiscountedPrice = getItemsPriceByType(ProductType.Mug)
        return vouchersPrice + tshirtsPrice + generalNonDiscountedPrice
    }

    /*
     * Calculate the checkout of the cart without applying the discounts
     */
    fun checkoutWithoutDiscount(): Double {
        val vouchersPrice = getItemsPriceWithoutDiscountByType(ProductType.Voucher)
        val tshirtsPrice = getItemsPriceWithoutDiscountByType(ProductType.Tshirt)
        val generalNonDiscountedPrice = getItemsPriceWithoutDiscountByType(ProductType.Mug)
        return vouchersPrice + tshirtsPrice + generalNonDiscountedPrice
    }

    /*
     * Calculate the total amount of items in the cart
     */
    fun productsNumber(): Int {
        var itemNumber = 0
        for ((_, value) in products) {
            itemNumber += value
        }
        return itemNumber
    }

    fun getDiscountedCountByType(quantity: Int, type: ProductType): Int = when (type) {
        ProductType.Voucher -> quantity / cartParams.voucherDiscountThreshold
        ProductType.Tshirt -> if (quantity >= cartParams.tshirtDiscountThreshold) quantity else 0
        ProductType.Mug, ProductType.Unknown -> 0
    }

    fun getItemsPriceWithoutDiscountByType(type: ProductType): Double = when (type) {
        ProductType.Voucher -> {
            products.toList().filter {
                it.first.type == ProductType.Voucher
            }.sumOf { getNotDiscountableItemsPrice(it.second, it.first.price) }
        }

        ProductType.Tshirt -> {
            products.toList().filter {
                it.first.type == ProductType.Tshirt
            }.sumOf { getNotDiscountableItemsPrice(it.second, it.first.price) }
        }

        ProductType.Mug, ProductType.Unknown -> {
            products.toList().filterNot {
                it.first.type in listOf(ProductType.Voucher, ProductType.Tshirt)
            }.sumOf { getNotDiscountableItemsPrice(it.second, it.first.price) }
        }
    }


    fun getItemsPriceByType(type: ProductType): Double {
        var totalAmountByType = 0.0
        products.filter { it.key.type == type }
            .forEach { totalAmountByType += getItemsPriceByAndName(it.key.type, it.key.name) }
        return totalAmountByType
    }

    fun getItemsPriceByAndName(type: ProductType, name: String?) = when (type) {
        ProductType.Voucher -> {
            with(
                products.toList()
                    .find { findItemByTypeAndName(it.first, ProductType.Voucher, name) }) {
                getVouchersPrice(
                    this?.second ?: 0,
                    this?.first?.price ?: cartParams.voucherDefaultPrice
                )
            }
        }

        ProductType.Tshirt -> {
            with(
                products.toList()
                    .find { findItemByTypeAndName(it.first, ProductType.Tshirt, name) }) {
                getTshirtsPrice(
                    this?.second ?: 0,
                    this?.first?.price ?: cartParams.tshirtDefaultPrice
                )
            }
        }

        ProductType.Mug -> {
            val product = products.toList()
                .find { findItemByTypeAndName(it.first, ProductType.Mug, name) }
            getNotDiscountableItemsPrice(product?.second ?: 0, product?.first?.price ?: 0.0)
        }

        ProductType.Unknown -> {
            0.0
        }
    }

    private fun findItemByTypeAndName(
        product: ProductBo, type: ProductType, name: String?
    ) =
        if (name != null) {
            product.type == type && name == product.name
        } else {
            product.type == ProductType.Voucher
        }

    private fun getVouchersPrice(count: Int, price: Double) =
        (count - (count / cartParams.voucherDiscountThreshold)) * price

    private fun getTshirtsPrice(count: Int, price: Double)
            : Double = when {
        count >= cartParams.tshirtDiscountThreshold -> count * (price - cartParams.discountedTshirtPrice)
        else -> count * price
    }

    private fun getNotDiscountableItemsPrice(count: Int, price: Double)
            : Double = count * price

    fun getItemsPriceWithoutDiscountByTypeAndName(type: ProductType, name: String): Double {
        val products =
            products.toList().find { findItemByTypeAndName(it.first, type, name) }
        return getNotDiscountableItemsPrice(
            products?.second ?: 0,
            products?.first?.price ?: cartParams.voucherDefaultPrice
        )
    }


}