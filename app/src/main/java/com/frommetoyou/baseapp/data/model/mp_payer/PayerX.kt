package com.frommetoyou.baseapp.data.model.mp_payer

data class PayerX(
    val address: Address,
    val date_created: String,
    val email: String,
    val identification: Identification,
    val name: String,
    val phone: Phone,
    val surname: String
)