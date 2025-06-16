package com.target.targetcasestudy.usecase

import com.target.targetcasestudy.data.model.Deal
import com.target.targetcasestudy.data.repository.DealRepository
import com.target.targetcasestudy.domain.usecase.RetrieveDeal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class RetrieveDealByIdTest {

    private lateinit var dealRepository: DealRepository
    private lateinit var retrieveDeal: RetrieveDeal

    private val dummyDeal = Deal(
            id = 2,
            title = "Test Deal",
            aisle = "B1",
            description = "Test description",
            imageUrl = null,
            regularPrice = mock(),
            salePrice = null,
            fulfillment = "Online",
            availability = "In stock"
        )

    @Before
    fun setUp() {
        dealRepository = mock()
        retrieveDeal = RetrieveDeal(dealRepository)
    }

    @Test
    fun `invoke should return list of deals from repository`() = runTest {
        // Arrange
        `when`(dealRepository.retrieveDeal(dummyDeal.id)).thenReturn(dummyDeal)

        // Act
        val result = retrieveDeal.invoke(dummyDeal.id)

        // Assert
        assertEquals(dummyDeal, result)
        verify(dealRepository).retrieveDeals()
    }
}