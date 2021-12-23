package com.frommetoyou.baseapp.presentation.ui.uimodel

import com.frommetoyou.baseapp.data.util.Event

data class MainFragmentUiModel(
    val onShowMessageEvent: Event<String>? = null,
    val onOpenMPCheckout: Event<String>? = null,
)