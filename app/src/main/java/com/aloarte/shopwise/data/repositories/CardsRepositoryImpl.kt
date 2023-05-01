package com.aloarte.shopwise.data.repositories

import com.aloarte.shopwise.data.datasources.CardsDatasource
import com.aloarte.shopwise.domain.model.CardBo
import com.aloarte.shopwise.domain.repositories.CardsRepository
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val datasource: CardsDatasource,
) : CardsRepository {
    override suspend fun fetchUserCards(): List<CardBo> = datasource.fetchUserCards()

}