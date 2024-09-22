package com.mohaberabi.kdownloader.data

import com.mohaberabi.kdownloader.domain.AppFileInfo
import com.mohaberabi.kdownloader.domain.FileUploadRepository
import com.mohaberabi.kdownloader.domain.UploadProgress
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn

class DefaultFileUplaoderRepository(
    private val httpClient: HttpClient,
    private val ioDispatcher: CoroutineDispatcher,
) : FileUploadRepository {
    override fun upload(
        fileInfo: AppFileInfo,
    ): Flow<UploadProgress> {
        return channelFlow {
            val bytes = fileInfo.bytes
            val name = fileInfo.name
            val mime = fileInfo.name

            httpClient.submitFormWithBinaryData(
                url = "https://dlptest.com/https-post/",
                formData = formData {
                    append("description", "Test")
                    append("name", name)
                    append(
                        "the_file", bytes,
                        Headers.build {
                            append(HttpHeaders.ContentType, mime)
                            append(HttpHeaders.ContentDisposition, "filename=${name}")

                        },
                    )
                }
            ) {
                onUpload { bytesSentTotal, contentLength ->
                    val progress = UploadProgress(
                        totalBytes = contentLength,
                        bytesSent = bytesSentTotal,
                    )
                    trySend(progress)
                }
            }
        }.flowOn(ioDispatcher)

    }

}