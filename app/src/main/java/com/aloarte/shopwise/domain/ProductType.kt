package com.aloarte.shopwise.domain

enum class ProductType {
    Voucher, Tshirt, Mug, Unknown;

    companion object {
        fun fromString(value: String): ProductType = if(value == "No filter") Unknown else ProductType.valueOf(value)
    }
}

