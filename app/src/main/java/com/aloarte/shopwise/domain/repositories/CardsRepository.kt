package com.aloarte.shopwise.domain.repositories

import com.aloarte.shopwise.domain.model.CardBo

interface CardsRepository {

    suspend fun fetchUserCards(): List<CardBo>
}