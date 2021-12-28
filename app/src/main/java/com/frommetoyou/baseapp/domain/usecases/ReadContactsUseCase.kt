package com.frommetoyou.baseapp.domain.usecases

import android.content.Context
import android.provider.ContactsContract
import com.frommetoyou.baseapp.data.extensions.loading
import com.frommetoyou.baseapp.data.util.CoroutinesDispatcherProvider
import com.frommetoyou.baseapp.data.util.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ReadContactsUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) {
    suspend operator fun invoke(): Flow<Result<String>> = flow {
        val projection = arrayOf(
            ContactsContract.Data.DISPLAY_NAME_PRIMARY,
            ContactsContract.Data.DATA1
        )
        val cursor = context.contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        cursor?.let {
            if (it.moveToFirst()) {
                var text = ""
                for (j in 0 until it.count) {
                    for (i in 0 until it.columnCount) {
                        text += it.getColumnName(i) + " : " + it.getString(i) + "\n"
                    }
                    it.moveToNext()
                }
                emit(Result.Success(text))
            }
            cursor.close()
        } ?: emit(Result.Error("Error al leer los contactos"))
    }
        .flowOn(coroutinesDispatcherProvider.io)
        .loading()
}