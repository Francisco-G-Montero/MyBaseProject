package com.frommetoyou.baseapp.data.services.dto.mercagopago

data class Item(
    val category_id: String,
    val currency_id: String,
    val description: String,
    val id: String,
    val quantity: Int,
    val title: String,
    val unit_price: Int
)