package com.aloarte.shopwise.domain

class ShoppingCart(private val cartParams: ShoppingCartParams) {

    private val products: MutableMap<ProductBo, Int> = mutableMapOf()

    fun addItem(product: ProductBo, quantity: Int) {
        products[product] = products.getOrDefault(product, 0) + quantity
    }

    fun checkout(): Double {
        val productsList = products.toList()
        val vouchersPrice = with(productsList.find { it.first.type == ProductType.Voucher }) {
            getVouchersPrice(
                this?.second ?: 0,
                this?.first?.price ?: cartParams.voucherDefaultPrice
            )
        }
        val tshirtsPrice = with(productsList.find { it.first.type == ProductType.Tshirt }) {
            getTshirtsPrice(this?.second ?: 0, this?.first?.price ?: cartParams.tshirtDefaultPrice)
        }
        // If its anything different than a Voucher or a Tshirt (A Mug)
        val nonDiscountedItemsPrice = productsList.filterNot {
            it.first.type in listOf(ProductType.Voucher, ProductType.Tshirt)
        }.sumOf { it.second * it.first.price }

        return vouchersPrice + tshirtsPrice + nonDiscountedItemsPrice
    }

    fun productsNumber(): Int {
        var itemNumber = 0
        for ((_, value) in products) {
            itemNumber += value
        }
        return itemNumber
    }

    private fun getVouchersPrice(voucherCount: Int, voucherPrice: Double) =
        (voucherCount - (voucherCount / cartParams.voucherDiscountThreshold)) * voucherPrice

    private fun getTshirtsPrice(tshirtCount: Int, shirtsPrice: Double) = when {
        tshirtCount >= cartParams.tshirtDiscountThreshold -> tshirtCount * cartParams.discountedTshirtPrice
        else -> tshirtCount * shirtsPrice
    }

}