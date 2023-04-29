package com.aloarte.shopwise.presentation

import com.aloarte.shopwise.R
import com.aloarte.shopwise.domain.ProductType
import com.aloarte.shopwise.presentation.ui.theme.MugBackground
import com.aloarte.shopwise.presentation.ui.theme.TshirtBackground
import com.aloarte.shopwise.presentation.ui.theme.VoucherBackground


fun ProductType.getProductImage() = when (this) {
    ProductType.Voucher -> R.drawable.ic_voucher
    ProductType.Tshirt -> R.drawable.ic_tshirt
    ProductType.Mug -> R.drawable.ic_mug
    else -> R.drawable.ic_voucher
}

fun ProductType.getProductBackground() = when (this) {
    ProductType.Voucher -> VoucherBackground
    ProductType.Tshirt -> TshirtBackground
    ProductType.Mug -> MugBackground
    else -> MugBackground
}
