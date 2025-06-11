package com.target.targetcasestudy.repository

import com.target.targetcasestudy.data.api.DealApiKtx
import com.target.targetcasestudy.data.api.DealResponse
import com.target.targetcasestudy.data.model.Deal
import com.target.targetcasestudy.data.repository.DealRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class DealRepositoryTest {

    private lateinit var dealApi: DealApiKtx
    private lateinit var repository: DealRepository

    private val dummyDeal = Deal(
        id = 1,
        title = "Test",
        aisle = "A1",
        description = "desc",
        imageUrl = null,
        regularPrice = mock(),
        salePrice = null,
        fulfillment = "Online",
        availability = "In Stock"
    )

    /**
     * Mock API
     */
    @Before
    fun setUp() {
        dealApi = mock()
        repository = DealRepository(dealApi)
    }

    /**
     * Check if API returns a list of deals successfully
     */
    @Test
    fun `retrieveDeals should return list from API`() = runTest {
        val dummyResponse = DealResponse(listOf(dummyDeal))
        `when`(dealApi.retrieveDeals()).thenReturn(dummyResponse)

        val result = repository.retrieveDeals()

        assertEquals(dummyResponse.deals, result)
        verify(dealApi).retrieveDeals()
    }

    /**
     * Check if API returns a single deal object via specified ID
     */
    @Test
    fun `retrieveDeal should return single deal from API`() = runTest {
        `when`(dealApi.retrieveDeal(1)).thenReturn(dummyDeal)

        val result = repository.retrieveDeal(1)

        assertEquals(dummyDeal, result)
        verify(dealApi).retrieveDeal(1)
    }
}
