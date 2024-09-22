package com.mohaberabi.kdownloader.presentation.viewmodel

import android.telephony.mbms.FileInfo
import com.mohaberabi.kdownloader.domain.AppFileInfo

data class UploadState(
    val isUploading: Boolean = false,
    val progress: Float = 0f,
)
