package com.target.targetcasestudy.data.api

import com.target.targetcasestudy.data.model.Deal
import com.target.targetcasestudy.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path

interface DealApiKtx {

  @GET("${Constants.BASE_URL}deals")
  suspend fun retrieveDeals(): DealResponse

  @GET("${Constants.BASE_URL}deals/{dealId}")
  suspend fun retrieveDeal(@Path("dealId") dealId: String): Deal
}
