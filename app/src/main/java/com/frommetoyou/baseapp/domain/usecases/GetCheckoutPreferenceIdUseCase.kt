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
    suspend operator fun invoke(amount: Int): Flow<Result<CheckoutPreferenceDto>> {
        val accessToken = context.getString(R.string.mercadopago_access_token)
        val dummyRequest = createCheckoutRequest(amount)
        return mpCheckoutRepository.getCheckoutPreferenceId(accessToken, dummyRequest)
    }

    private fun createCheckoutRequest(amount: Int): MPCheckoutRequest {
        val item = Item(
            "ARS",
            "Teclado de 64 teclas. Negro. Nuevo",
            1,
            "Teclado Korg ESP256",
            amount.toDouble()
        )
        val payer = Payer("gabixp45@gmail.com")
        val itemList = listOf(item)
        return MPCheckoutRequest(itemList, payer)
    }
}