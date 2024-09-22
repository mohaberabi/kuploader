package com.mohaberabi.kdownloader.domain

interface FileReader {


    suspend fun getFileFromUri(
        uriString: String
    ): AppFileInfo
}