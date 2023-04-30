package com.aloarte.shopwise.domain

class ShoppingCart(private val cartParams: ShoppingCartParams) {

    private val products: MutableMap<ProductBo, Int> = mutableMapOf()

    /* Add a product to the map. Update if already exist. Add the quantity of items
    * */
    fun addItem(product: ProductBo, quantity: Int) {
        products[product] = products.getOrDefault(product, 0) + quantity
    }

    fun resetItem(product: ProductBo) {
        products[product] = 0
    }

    fun removeItem(product: ProductBo) {
        products.remove(product)
    }

    fun getCartItems() = products.toList()

    fun checkout(): Double {
        val vouchersPrice = getItemsPriceByType(ProductType.Voucher)
        val tshirtsPrice = getItemsPriceByType(ProductType.Tshirt)
        val generalNonDiscountedPrice = getItemsPriceByType(ProductType.Mug)
        return vouchersPrice + tshirtsPrice + generalNonDiscountedPrice
    }

    fun checkoutWithoutDiscount(): Double{
        val vouchersPrice = getItemsPriceWithoutDiscountByType(ProductType.Voucher)
        val tshirtsPrice = getItemsPriceWithoutDiscountByType(ProductType.Tshirt)
        val generalNonDiscountedPrice = getItemsPriceWithoutDiscountByType(ProductType.Mug)
        return vouchersPrice + tshirtsPrice + generalNonDiscountedPrice
    }

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

    fun getItemsPriceByType(type: ProductType) = when (type) {
        ProductType.Voucher -> {
            with(products.toList().find { it.first.type == ProductType.Voucher }) {
                getVouchersPrice(
                    this?.second ?: 0,
                    this?.first?.price ?: cartParams.voucherDefaultPrice
                )
            }
        }

        ProductType.Tshirt -> {
            with(products.toList().find { it.first.type == ProductType.Tshirt }) {
                getTshirtsPrice(
                    this?.second ?: 0,
                    this?.first?.price ?: cartParams.tshirtDefaultPrice
                )
            }
        }

        ProductType.Mug, ProductType.Unknown -> {
            products.toList().filterNot {
                it.first.type in listOf(ProductType.Voucher, ProductType.Tshirt)
            }.sumOf { getNotDiscountableItemsPrice(it.second, it.first.price) }
        }
    }

    private fun getVouchersPrice(count: Int, price: Double) =
        (count - (count / cartParams.voucherDiscountThreshold)) * price

    private fun getTshirtsPrice(count: Int, price: Double): Double = when {
        count >= cartParams.tshirtDiscountThreshold -> count * cartParams.discountedTshirtPrice
        else -> count * price
    }

    private fun getNotDiscountableItemsPrice(count: Int, price: Double): Double = count * price



}