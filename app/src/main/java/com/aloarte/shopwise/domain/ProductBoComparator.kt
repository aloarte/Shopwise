package com.aloarte.shopwise.domain

class ProductBoComparator {

    companion object : Comparator<Pair<ProductBo, Int>> {

        override fun compare(a: Pair<ProductBo, Int>, b: Pair<ProductBo, Int>): Int {
            val productA = a.first
            val productB = b.first
            return when {
                productA.type == ProductType.Voucher && productB.type == ProductType.Tshirt -> -1
                productA.type == ProductType.Voucher && productB.type == ProductType.Mug -> -1
                productA.type == ProductType.Tshirt && productB.type == ProductType.Mug -> -1
                else -> 1
            }
        }
    }
}