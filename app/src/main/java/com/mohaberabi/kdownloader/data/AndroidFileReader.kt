package com.mohaberabi.kdownloader.data

import android.content.Context
import android.net.Uri
import com.mohaberabi.kdownloader.domain.AppFileInfo
import com.mohaberabi.kdownloader.domain.FileReader
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID

class AndroidFileReader(
    private val ioDispatcher: CoroutineDispatcher,
    private val context: Context,
) : FileReader {
    override suspend fun getFileFromUri(
        uriString: String,
    ): AppFileInfo {
        return withContext(ioDispatcher) {
            val uri = Uri.parse(uriString)
            val bytes = context.contentResolver.openInputStream(uri)?.use {
                it.readBytes()
            } ?: byteArrayOf()
            val name = UUID.randomUUID().toString()
            val mimeType = context.contentResolver.getType(uri) ?: ""
            AppFileInfo(
                name = name,
                bytes = bytes,
                mimeType = mimeType,
            )
        }

    }
}

