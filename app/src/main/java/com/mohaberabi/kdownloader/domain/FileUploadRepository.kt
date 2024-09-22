package com.mohaberabi.kdownloader.domain

import kotlinx.coroutines.flow.Flow

interface FileUploadRepository {


    fun upload(fileInfo: AppFileInfo): Flow<UploadProgress>
}