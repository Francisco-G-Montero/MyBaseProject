package com.frommetoyou.baseapp.data.extensions

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import com.frommetoyou.baseapp.data.util.Result

@ExperimentalCoroutinesApi
fun <T> Flow<Result<T>>.loading(): Flow<Result<T>> =
    this.onStart { emit(Result.StartLoading) }
        .onCompletion { emit(Result.StopLoading) }

@InternalCoroutinesApi
suspend fun <T, R: Result<T>> Flow<R>.collectWithLoading(load: MutableLiveData<Boolean>,
                                                                action: suspend (value: R) -> Unit) = collect {
    when(it) {
        is Result.StartLoading -> load.value = true
        is Result.StopLoading -> load.value = false
        else -> action(it)
    }
}
