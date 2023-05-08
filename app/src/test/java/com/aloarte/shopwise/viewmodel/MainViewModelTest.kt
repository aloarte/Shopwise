package com.aloarte.shopwise.viewmodel

import com.aloarte.shopwise.domain.model.PurchaseDataItem
import com.aloarte.shopwise.domain.repositories.CardsRepository
import com.aloarte.shopwise.domain.repositories.ShopwiseProductsRepository
import com.aloarte.shopwise.presentation.MainViewModel
import com.aloarte.shopwise.presentation.UiState
import com.aloarte.shopwise.presentation.compose.enums.PaymentMethodType
import com.aloarte.shopwise.utils.CoroutinesTestRule
import com.aloarte.shopwise.utils.TestData
import com.aloarte.shopwise.utils.TestData.TSHIRT_PRICE
import com.aloarte.shopwise.utils.TestData.cardsBoList
import com.aloarte.shopwise.utils.TestData.rTshirt
import com.aloarte.shopwise.utils.TestData.remoteProductsBoList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    companion object {
        const val quantity = 2
    }

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    private lateinit var productsRepository: ShopwiseProductsRepository

    @MockK
    private lateinit var cardsRepository: CardsRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(productsRepository, cardsRepository)
    }

    @Test
    fun `test fetch items from remote`() = coroutinesTestRule.runBlockingTest {
        coEvery { productsRepository.fetchProducts(true) } returns remoteProductsBoList
        coEvery { cardsRepository.fetchUserCards() } returns cardsBoList

        viewModel.fetchItems(true)

        coVerify { productsRepository.fetchProducts(true) }
        coVerify { cardsRepository.fetchUserCards() }
        val expectedState = UiState(
            cards = cardsBoList,
            productList = remoteProductsBoList

        )
        assertStateEqualsWithoutCart(expectedState, viewModel.state.first())
    }

    @Test
    fun `test add item to cart`() = coroutinesTestRule.runBlockingTest {
        viewModel.addItemToCart(product = rTshirt, quantity = quantity)

        val expectedState = UiState(
            cartValue = quantity * TSHIRT_PRICE,
            cartSize = quantity
        )
        assertStateEqualsWithoutCart(expectedState, viewModel.state.first())
    }

    @Test
    fun `test add item to cart with replace`() = coroutinesTestRule.runBlockingTest {
        viewModel.addItemToCart(product = rTshirt, quantity = quantity)
        viewModel.addItemToCart(replace = true, product = rTshirt, quantity = quantity)

        val expectedState = UiState(
            cartValue = quantity * TSHIRT_PRICE,
            cartSize = quantity
        )
        assertStateEqualsWithoutCart(expectedState, viewModel.state.first())
    }

    @Test
    fun `test remove item from cart`() = coroutinesTestRule.runBlockingTest {
        viewModel.addItemToCart(product = rTshirt, quantity = quantity)
        val expectedPreviousState = UiState(
            cartValue = quantity * TSHIRT_PRICE,
            cartSize = quantity
        )
        assertStateEqualsWithoutCart(expectedPreviousState, viewModel.state.first())

        viewModel.removeItemFromCart(product = rTshirt)

        val expectedState = UiState(
            cartValue = 0.0,
            cartSize = 0
        )
        assertStateEqualsWithoutCart(expectedState, viewModel.state.first())
    }

    @Test
    fun `test clear cart and state`() = coroutinesTestRule.runBlockingTest {
        val purchaseData = listOf(
            PurchaseDataItem(name = TestData.TSHIRT_CODE, 1)
        )
        coEvery { productsRepository.getPurchaseData(any()) } returns purchaseData
        viewModel.addItemToCart(product = rTshirt, quantity = quantity)
        val expectedPreviousState = UiState(
            cartValue = quantity * TSHIRT_PRICE,
            cartSize = quantity
        )
        assertStateEqualsWithoutCart(expectedPreviousState, viewModel.state.first())

        viewModel.clearCartAndState()

        coVerify { productsRepository.getPurchaseData(any()) }
        val expectedState = UiState(
            cartValue = 0.0,
            cartSize = 0,
            purchaseData =  purchaseData
        )
        assertStateEqualsWithoutCart(expectedState, viewModel.state.first())
    }

    @Test
    fun `test change payment type`() = coroutinesTestRule.runBlockingTest {
        viewModel.changePaymentType(PaymentMethodType.Paypal)

        val expectedState = UiState(
            selectedPaymentMethod = PaymentMethodType.Paypal
        )

        assertStateEqualsWithoutCart(expectedState, viewModel.state.first())
    }

    private fun assertStateEqualsWithoutCart(expectedState: UiState, updatedState: UiState) {
        Assert.assertEquals(expectedState.cards, updatedState.cards)
        Assert.assertEquals(expectedState.productList, updatedState.productList)
        Assert.assertEquals(expectedState.selectedPaymentMethod, updatedState.selectedPaymentMethod)
        Assert.assertEquals(expectedState.cartSize, updatedState.cartSize)
        Assert.assertEquals(expectedState.cartValue, updatedState.cartValue, 0.01)

    }
}