package com.aloarte.shopwise.domain.model

import com.aloarte.shopwise.domain.enums.CardPaymentNetworkType
import com.aloarte.shopwise.domain.enums.CardType

data class CardBo(
    val type: CardType,
    val paymentNetworkType: CardPaymentNetworkType,
    val cardNumber: String,
    val ownerName: String,
    val ccv: String,
    val expirationDate:String
)