package com.frommetoyou.baseapp.domain.repository

import com.frommetoyou.baseapp.data.model.MPCheckoutRequest
import com.frommetoyou.baseapp.data.services.dto.mercagopago.CheckoutPreferenceDto
import com.frommetoyou.baseapp.data.util.Result
import kotlinx.coroutines.flow.Flow

interface MPCheckoutRepository {
    suspend fun getCheckoutPreferenceId(
        accessToken: String,
        mpCheckoutRequest: MPCheckoutRequest
    ): Flow<Result<CheckoutPreferenceDto>>
}