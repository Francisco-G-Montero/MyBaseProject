package com.frommetoyou.baseapp.data.util

sealed class Result<out T>{
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorMessage: String) : Result<Nothing>()
    data class InputError<T>(val messageToInputMap: Map<String, T>) : Result<Nothing>()
    object StartLoading : Result<Nothing>()
    object StopLoading : Result<Nothing>()
}