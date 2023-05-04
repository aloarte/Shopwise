package com.aloarte.shopwise.data.datasources

import com.aloarte.shopwise.domain.model.CardBo


interface CardsDatasource {

    suspend fun fetchUserCards(): List<CardBo>

}