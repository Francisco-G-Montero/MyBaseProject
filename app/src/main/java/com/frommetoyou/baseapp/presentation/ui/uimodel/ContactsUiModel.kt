package com.frommetoyou.baseapp.presentation.ui.uimodel

import com.frommetoyou.baseapp.data.model.NullModel
import com.frommetoyou.baseapp.data.util.Event

data class ContactsUiModel(
    val onReadContactsGrantedEvent: Event<NullModel>? = null,
    val onShowContactsEvent: Event<String>? = null,
    val onShowMessage: Event<String>? = null,
)