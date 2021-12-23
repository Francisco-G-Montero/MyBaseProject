package com.frommetoyou.baseapp.data.services.dto.mercagopago

data class PaymentMethods(
    val default_card_id: Any,
    val default_installments: Any,
    val default_payment_method_id: Any,
    val excluded_payment_methods: List<ExcludedPaymentMethod>,
    val excluded_payment_types: List<ExcludedPaymentType>,
    val installments: Any
)