package com.target.targetcasestudy.ui.deal_details

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.target.targetcasestudy.data.model.Deal
import com.target.targetcasestudy.data.model.Price
import com.target.targetcasestudy.domain.usecase.RetrieveDeal
import com.target.targetcasestudy.ui.deal_detail.screens.DealDetailScreen
import com.target.targetcasestudy.ui.deal_detail.viewmodel.DealDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
class DealDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var useCase: RetrieveDeal
    private lateinit var viewModel: DealDetailViewModel

    private val dummyDeal = Deal(
        id = 1,
        title = "Test Product",
        aisle = "A1",
        description = "Test Description",
        imageUrl = null,
        regularPrice = Price(999, "$", "$9.99"),
        salePrice = null,
        fulfillment = "Pickup",
        availability = "In stock"
    )

    @Before
    fun setUp() {
        useCase = mock()
    }

    @Test
    fun successState_showsDetailContent() = runTest {
        whenever(useCase.invoke(1)).thenReturn(dummyDeal)
        viewModel = DealDetailViewModel(useCase)

        composeTestRule.setContent {
            DealDetailScreen(
                dealId = 1,
                onBackClick = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Test Product").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Description").assertIsDisplayed()
    }

    @Test
    fun errorState_showsErrorText() = runTest {
        whenever(useCase.invoke(1)).thenThrow(RuntimeException("Oops"))
        viewModel = DealDetailViewModel(useCase)

        composeTestRule.setContent {
            DealDetailScreen(
                dealId = 1,
                onBackClick = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Oops").assertExists()
    }

    @Test
    fun loadingState_showsProgressIndicator() = runTest {
        // Delay simulates loading state
        whenever(useCase.invoke(1)).thenAnswer{
            Thread.sleep(1000)
            dummyDeal
        }
        viewModel = DealDetailViewModel(useCase)

        composeTestRule.setContent {
            DealDetailScreen(
                dealId = 1,
                onBackClick = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithContentDescription("Loading").assertExists()
    }
}
