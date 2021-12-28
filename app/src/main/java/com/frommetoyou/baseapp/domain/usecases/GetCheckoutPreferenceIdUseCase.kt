package com.frommetoyou.baseapp.domain.usecases

import android.content.Context
import com.frommetoyou.baseapp.R
import com.frommetoyou.baseapp.data.model.Item
import com.frommetoyou.baseapp.data.model.MPCheckoutRequest
import com.frommetoyou.baseapp.data.model.Payer
import com.frommetoyou.baseapp.data.services.dto.mercagopago.CheckoutPreferenceDto
import com.frommetoyou.baseapp.data.util.Result
import com.frommetoyou.baseapp.domain.repository.MPCheckoutRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCheckoutPreferenceIdUseCase @Inject constructor(
    private val mpCheckoutRepository: MPCheckoutRepository,
    @ApplicationContext private val context: Context,
) {
    suspend operator fun invoke(
        amount: Int,
        itemName: String,
        itemDesc: String,
        itemQuantity: Int
    ): Flow<Result<CheckoutPreferenceDto>> {
        val accessToken = context.getString(R.string.mercadopago_access_token)
        val dummyRequest = createCheckoutRequest(
            amount,
            itemName,
            itemDesc,
            itemQuantity
        )
        return mpCheckoutRepository.getCheckoutPreferenceId(accessToken, dummyRequest)
    }

    private fun createCheckoutRequest(
        amount: Int,
        itemName: String,
        itemDesc: String,
        itemQuantity: Int
    ): MPCheckoutRequest {
        val item = Item(
            "ARS",
            itemDesc,
            itemQuantity,
            itemName,
            amount.toDouble()
        )
        val payer = Payer("test@testuser.com")
        val itemList = listOf(item)
        return MPCheckoutRequest(itemList, payer)
    }
}