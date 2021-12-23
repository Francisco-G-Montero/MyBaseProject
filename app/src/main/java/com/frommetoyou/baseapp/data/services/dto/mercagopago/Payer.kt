package com.frommetoyou.baseapp.data.services.dto.mercagopago

data class Payer(
    val address: Address,
    val date_created: Any,
    val email: String,
    val identification: Identification,
    val last_purchase: Any,
    val name: String,
    val phone: Phone,
    val surname: String
)