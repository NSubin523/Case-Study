package com.target.targetcasestudy.usecase

import com.target.targetcasestudy.data.model.Deal
import com.target.targetcasestudy.data.repository.DealRepository
import com.target.targetcasestudy.domain.usecase.RetrieveDeals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class RetrieveDealsTest {

    private lateinit var dealRepository: DealRepository
    private lateinit var retrieveDeals: RetrieveDeals

    private val dummyDeals = listOf(
        Deal(
            id = 1,
            title = "Test Deal",
            aisle = "A1",
            description = "Test description",
            imageUrl = null,
            regularPrice = mock(),
            salePrice = null,
            fulfillment = "Ship",
            availability = "In stock"
        )
    )

    @Before
    fun setUp() {
        dealRepository = mock()
        retrieveDeals = RetrieveDeals(dealRepository)
    }

    @Test
    fun `invoke should return list of deals from repository`() = runTest {
        // Arrange
        `when`(dealRepository.retrieveDeals()).thenReturn(dummyDeals)

        // Act
        val result = retrieveDeals.invoke()

        // Assert
        assertEquals(dummyDeals, result)
        verify(dealRepository).retrieveDeals()
    }
}
