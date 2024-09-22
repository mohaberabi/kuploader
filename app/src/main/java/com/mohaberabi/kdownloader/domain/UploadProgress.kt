package com.mohaberabi.kdownloader.domain

data class UploadProgress(
    val bytesSent: Long,
    val totalBytes: Long,
) {

    val progress: Long
        get() = if (totalBytes > 0) bytesSent / totalBytes else 0
}
