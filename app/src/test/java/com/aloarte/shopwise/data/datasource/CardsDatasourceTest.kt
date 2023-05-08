package com.aloarte.shopwise.data.datasource

import com.aloarte.shopwise.data.datasources.CardsDatasource
import com.aloarte.shopwise.data.datasources.CardsDatasourceImpl
import com.aloarte.shopwise.utils.TestData.cardsBoList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CardsDatasourceTest {

    private lateinit var datasource: CardsDatasource

    @Before
    fun setUp() {
        datasource = CardsDatasourceImpl()
    }

    @Test
    fun `test fetch products success`() {
        val cards = runBlocking {
            datasource.fetchUserCards()
        }

        Assert.assertEquals(cardsBoList, cards)
    }
}