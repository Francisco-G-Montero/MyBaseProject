package com.frommetoyou.baseapp.data.repository

import com.frommetoyou.baseapp.data.extensions.getResponseError
import com.frommetoyou.baseapp.data.extensions.parseResponse
import com.frommetoyou.baseapp.data.model.MPCheckoutRequest
import com.frommetoyou.baseapp.data.services.api.MPCheckoutApiService
import com.frommetoyou.baseapp.data.services.dto.mercagopago.CheckoutPreferenceDto
import com.frommetoyou.baseapp.data.util.CoroutinesDispatcherProvider
import com.frommetoyou.baseapp.data.util.Result
import com.frommetoyou.baseapp.domain.repository.MPCheckoutRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MPCheckoutRepositoryImpl @Inject constructor(
    private val mpCheckoutApiService: MPCheckoutApiService,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : MPCheckoutRepository {
    override suspend fun getCheckoutPreferenceId(
        accessToken: String,
        mpCheckoutRequest: MPCheckoutRequest
    ): Flow<Result<CheckoutPreferenceDto>> = flow {
        emit(mpCheckoutApiService.getCheckoutPreferenceId(accessToken, mpCheckoutRequest))
    }
        .catch { error ->
            error
            emit(error.getResponseError())
        }
        .map { result -> result.parseResponse() }
        .flowOn(coroutinesDispatcherProvider.io)
}