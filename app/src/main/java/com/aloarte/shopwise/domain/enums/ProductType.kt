package com.aloarte.shopwise.domain.enums

enum class ProductType {
    Voucher, Tshirt, Mug, Unknown;

    companion object {
        fun fromString(value: String): ProductType = try {
            ProductType.valueOf(value)
        } catch (e: IllegalArgumentException) {
            Unknown
        }
    }
}

