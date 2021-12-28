package com.intermedia.daiana.scan.domain.usecases.local

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RequestPermissionsUseCase @Inject constructor(
    @ApplicationContext private val context: Context
){
    operator fun invoke(
        permission: String,
        activityResult: ActivityResultLauncher<String>,
        callback: () -> (Unit)
    ) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                callback()
            }
            else -> {
                activityResult.launch(permission)
            }
        }
    }
}