package com.aloarte.shopwise.domain

import com.aloarte.shopwise.data.datasources.CardsDatasource
import com.aloarte.shopwise.data.repositories.CardsRepositoryImpl
import com.aloarte.shopwise.domain.repositories.CardsRepository
import com.aloarte.shopwise.utils.TestData.cardsBoList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CardsRepositoryTest {

    @MockK
    private lateinit var datasource: CardsDatasource

    private lateinit var repository: CardsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CardsRepositoryImpl(datasource)
    }

    @Test
    fun `test fetch products success`() {
        coEvery { datasource.fetchUserCards() } returns cardsBoList

        val cardsResponse = runBlocking { repository.fetchUserCards() }

        coVerify { datasource.fetchUserCards() }
        Assert.assertEquals(cardsBoList, cardsResponse)
    }

}