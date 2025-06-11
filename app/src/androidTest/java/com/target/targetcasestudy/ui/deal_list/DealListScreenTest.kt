package com.target.targetcasestudy.ui.deal_list

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.target.targetcasestudy.data.model.Deal
import com.target.targetcasestudy.data.model.Price
import com.target.targetcasestudy.domain.usecase.RetrieveDeals
import com.target.targetcasestudy.ui.deal_list.screens.DealListScreen
import com.target.targetcasestudy.ui.deal_list.viewmodel.DealListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class DealListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var useCase: RetrieveDeals
    private lateinit var viewModel: DealListViewModel

    private val dummyDeal = Deal(
        id = 1,
        title = "Test Product",
        aisle = "A1",
        description = "Test Description",
        imageUrl = null,
        regularPrice = Price(999, "$", "$9.99"),
        salePrice = Price(799, "$", "$7.99"),
        fulfillment = "Online",
        availability = "In stock"
    )

    @Before
    fun setUp() {
        useCase = mock()
    }

    @Test
    fun successState_showsDealList() = runTest {
        whenever(useCase.invoke()).thenReturn(listOf(dummyDeal))
        viewModel = DealListViewModel(useCase)

        composeTestRule.setContent {
            DealListScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Test Product").assertIsDisplayed()
        composeTestRule.onNodeWithText("In stock in aisle A1").assertExists()
    }

    @Test
    fun errorState_showsErrorMessage() = runTest {
        whenever(useCase.invoke()).thenThrow(RuntimeException("Error"))
        viewModel = DealListViewModel(useCase)

        composeTestRule.setContent {
            DealListScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Error").assertExists()
    }

    @Test
    fun loadingState_showsCircularProgress() = runTest {
        whenever(useCase.invoke()).thenAnswer {
            Thread.sleep(1000)
            listOf(dummyDeal)
        }
        viewModel = DealListViewModel(useCase)

        composeTestRule.setContent {
            DealListScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithContentDescription("Loading").assertExists()
    }
}
