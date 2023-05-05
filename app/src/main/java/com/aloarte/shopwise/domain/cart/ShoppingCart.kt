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
    fun checkout(applyDiscounts: Boolean = true): Double {
        val vouchersPrice = getItemsPrice(ProductType.Voucher, applyDiscounts)
        val tshirtsPrice = getItemsPrice(ProductType.Tshirt, applyDiscounts)
        val generalNonDiscountedPrice = getItemsPrice(ProductType.Mug, applyDiscounts)
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

    fun areProductsDiscounted(quantity: Int, type: ProductType): Boolean = when (type) {
        ProductType.Voucher -> quantity / cartParams.voucherDiscountThreshold > 0
        ProductType.Tshirt -> quantity >= cartParams.tshirtDiscountThreshold
        ProductType.Mug, ProductType.Unknown -> false
    }


    fun getItemsPrice(type: ProductType, discounted: Boolean = true): Double {
        var totalAmountByType = 0.0
        products.filter { it.key.type == type }
            .forEach {
                totalAmountByType += if (discounted) {
                    getDiscountedItemsPrice(it.key.type, it.key.name)
                } else {
                    getNotDiscountedItemsPrice(it.key.type, it.key.name)
                }
            }
        return totalAmountByType
    }

    fun getDiscountedItemsPrice(type: ProductType, name: String) = when (type) {
        ProductType.Voucher -> {
            with(products.toList()
                .find { findItemByTypeAndName(it.first, ProductType.Voucher, name) }) {
                getDiscountedVouchersPrice(
                    this?.second ?: 0, this?.first?.price ?: cartParams.voucherDefaultPrice
                )
            }
        }
        ProductType.Tshirt -> {
            with(products.toList()
                .find { findItemByTypeAndName(it.first, ProductType.Tshirt, name) }) {
                getDiscountedTshirtsPrice(
                    this?.second ?: 0, this?.first?.price ?: cartParams.tshirtDefaultPrice
                )
            }
        }
        ProductType.Mug -> {
            val product =
                products.toList().find { findItemByTypeAndName(it.first, ProductType.Mug, name) }
            getNotDiscountableItemsPrice(product?.second ?: 0, product?.first?.price ?: 0.0)
        }
        ProductType.Unknown -> {
            0.0
        }
    }

    fun getNotDiscountedItemsPrice(type: ProductType, name: String): Double {
        val products = products.toList().find { findItemByTypeAndName(it.first, type, name) }
        return getNotDiscountableItemsPrice(
            products?.second ?: 0, products?.first?.price ?: cartParams.voucherDefaultPrice
        )
    }

    private fun findItemByTypeAndName(
        product: ProductBo, type: ProductType, name: String?
    ) = if (name != null) {
        product.type == type && name == product.name
    } else {
        product.type == ProductType.Voucher
    }

    private fun getDiscountedVouchersPrice(count: Int, price: Double) =
        (count - (count / cartParams.voucherDiscountThreshold)) * price

    private fun getDiscountedTshirtsPrice(count: Int, price: Double): Double = when {
        count >= cartParams.tshirtDiscountThreshold -> count * (price - cartParams.discountedTshirtPrice)
        else -> count * price
    }

    private fun getNotDiscountableItemsPrice(count: Int, price: Double): Double = count * price

}