package com.frommetoyou.baseapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.frommetoyou.baseapp.data.util.CoroutinesDispatcherProvider
import com.frommetoyou.baseapp.data.util.Event
import com.frommetoyou.baseapp.presentation.ui.uimodel.MainFragmentUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel  @Inject constructor(
    private val coroutinesDispatchers: CoroutinesDispatcherProvider,
): BaseViewModel() {
    private val _uiState = MutableLiveData<MainFragmentUiModel>()
    val uiState: LiveData<MainFragmentUiModel>
        get() = _uiState


    fun getMessage(s: String) = viewModelScope.launch {
        emitUiModel(onShowMessageEvent = Event(s))
    }

    private suspend fun emitUiModel(
        onShowMessageEvent: Event<String>? = null,
    ) = withContext(coroutinesDispatchers.main) {
        _uiState.value = MainFragmentUiModel(
            onShowMessageEvent = onShowMessageEvent,
        )
    }
}