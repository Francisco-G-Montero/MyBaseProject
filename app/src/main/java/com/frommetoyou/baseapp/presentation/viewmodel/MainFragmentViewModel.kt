package com.frommetoyou.baseapp.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.frommetoyou.baseapp.data.util.CoroutinesDispatcherProvider
import com.frommetoyou.baseapp.data.util.Event
import com.frommetoyou.baseapp.data.util.Result
import com.frommetoyou.baseapp.domain.usecases.GetCheckoutPreferenceIdUseCase
import com.frommetoyou.baseapp.presentation.ui.uimodel.MainFragmentUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val coroutinesDispatchers: CoroutinesDispatcherProvider,
    private val getCheckoutPreferenceIdUseCase: GetCheckoutPreferenceIdUseCase,
) : BaseViewModel() {
    private val _uiState = MutableLiveData<MainFragmentUiModel>()
    val uiState: LiveData<MainFragmentUiModel>
        get() = _uiState

    fun startMPCheckout(
        amount: Int,
        itemName: String,
        itemDesc: String,
        itemQuantity: Int
    ) = viewModelScope.launch {
        getCheckoutPreferenceIdUseCase(
            amount,
            itemName,
            itemDesc,
            itemQuantity
        ).collect { result ->
            when (result) {
                is Result.Success -> {
                    emitUiModel(onOpenMPCheckout = Event(result.data.id))
                }
                is Result.Error -> {
                    emitUiModel(onShowMessageEvent = Event(result.errorMessage))
                }
            }
        }
    }

    private suspend fun emitUiModel(
        onShowMessageEvent: Event<String>? = null,
        onOpenMPCheckout: Event<String>? = null,
    ) = withContext(coroutinesDispatchers.main) {
        _uiState.value = MainFragmentUiModel(
            onShowMessageEvent = onShowMessageEvent,
            onOpenMPCheckout = onOpenMPCheckout,
        )
    }
}