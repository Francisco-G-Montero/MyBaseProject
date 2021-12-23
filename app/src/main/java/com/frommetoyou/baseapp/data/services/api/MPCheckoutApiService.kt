package com.frommetoyou.baseapp.data.services.api

import com.frommetoyou.baseapp.data.model.MPCheckoutRequest
import com.frommetoyou.baseapp.data.services.dto.mercagopago.CheckoutPreferenceDto
import retrofit2.Response
import retrofit2.http.*

interface MPCheckoutApiService {
    @POST("checkout/preferences")
    suspend fun getCheckoutPreferenceId(
        @Query("access_token") accessToken: String,
        @Body mpCheckoutRequest: MPCheckoutRequest
    ): Response<CheckoutPreferenceDto>
}