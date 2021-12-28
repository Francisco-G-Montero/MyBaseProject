package com.frommetoyou.baseapp.presentation.viewmodel

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.frommetoyou.baseapp.data.extensions.collectWithLoading
import com.frommetoyou.baseapp.data.model.NullModel
import com.frommetoyou.baseapp.data.util.CoroutinesDispatcherProvider
import com.frommetoyou.baseapp.data.util.Event
import com.frommetoyou.baseapp.data.util.Result
import com.frommetoyou.baseapp.domain.usecases.ReadContactsUseCase
import com.frommetoyou.baseapp.domain.usecases.RequestPermissionsUseCase
import com.frommetoyou.baseapp.presentation.ui.uimodel.ContactsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider,
    private val requestPermissionsUseCase: RequestPermissionsUseCase,
    private val readContactsUseCase: ReadContactsUseCase
) : BaseViewModel() {
    private val _uiState = MutableLiveData<ContactsUiModel>()
    val uiState: LiveData<ContactsUiModel>
        get() = _uiState

    fun onReadContactsPermissionGranted() = viewModelScope.launch {
        emitUiModel(onReadContactsGrantedEvent = Event(NullModel()))
    }

    fun requestPermission(resultLauncher: ActivityResultLauncher<String>) =
        viewModelScope.launch {
            requestPermissionsUseCase(
                Manifest.permission.READ_CONTACTS,
                resultLauncher
            ) {
                onReadContactsPermissionGranted()
            }
        }

    fun getContactsData() = viewModelScope.launch {
        readContactsUseCase().collectWithLoading(_showLoading) { result ->
            when (result) {
                is Result.Success -> {
                    emitUiModel(onShowContactsEvent = Event(result.data))
                }
                is Result.Error -> {
                    emitUiModel(onShowMessage = Event(result.errorMessage))
                }
            }
        }
    }

    private suspend fun emitUiModel(
        onReadContactsGrantedEvent: Event<NullModel>? = null,
        onShowContactsEvent: Event<String>? = null,
        onShowMessage: Event<String>? = null,
    ) = withContext(coroutinesDispatcherProvider.main) {
        _uiState.value = ContactsUiModel(
            onReadContactsGrantedEvent = onReadContactsGrantedEvent,
            onShowContactsEvent = onShowContactsEvent,
            onShowMessage = onShowMessage,
        )
    }
}