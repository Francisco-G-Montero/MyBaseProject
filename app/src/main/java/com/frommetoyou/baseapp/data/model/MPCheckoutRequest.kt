package com.frommetoyou.baseapp.data.model

data class MPCheckoutRequest(
    val items: List<Item>,
    val payer: Payer
)