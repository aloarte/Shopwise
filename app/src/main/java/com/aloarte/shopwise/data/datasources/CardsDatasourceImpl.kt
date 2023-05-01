package com.aloarte.shopwise.data.datasources

import com.aloarte.shopwise.domain.enums.CardPaymentNetworkType
import com.aloarte.shopwise.domain.enums.CardType
import com.aloarte.shopwise.domain.model.CardBo
import javax.inject.Inject


class CardsDatasourceImpl @Inject constructor() : CardsDatasource {
    override suspend fun fetchUserCards(): List<CardBo> =
        listOf(
            CardBo(
                type = CardType.Credit,
                paymentNetworkType = CardPaymentNetworkType.Mastercard,
                cardNumber = "3678 2014 0205 7840",
                ownerName = "J. Doe",
                ccv = "502",
                expirationDate = "03/30"
            ),
            CardBo(
                type = CardType.Debit,
                paymentNetworkType = CardPaymentNetworkType.Visa,
                cardNumber = "5800 1020 9845 3614",
                ownerName = "J. Doe",
                ccv = "184",
                expirationDate = "11/32"
            ))


}