package com.mohaberabi.kdownloader.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.kdownloader.domain.AppFileInfo
import com.mohaberabi.kdownloader.domain.FileReader
import com.mohaberabi.kdownloader.domain.FileUploadRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UploadViewModel(
    private val fileReader: FileReader,
    private val repository: FileUploadRepository,
) : ViewModel() {


    private val _state = MutableStateFlow(UploadState())

    val state = _state.asStateFlow()
    private var uploadJob: Job? = null

    fun pickFileAndUpload(uriString: String) {
        viewModelScope.launch {
            val fileInfo = fileReader.getFileFromUri(uriString)
            uploadFile(fileInfo)
        }
    }

    private fun uploadFile(fileInfo: AppFileInfo) {
        uploadJob = repository.upload(fileInfo)
            .onStart {
                _state.update { it.copy(isUploading = true) }
            }.onEach { progress ->
                _state.update { it.copy(progress = progress.progress.toFloat()) }
            }.onCompletion { cause ->
                if (cause == null) {
                    _state.update { it.copy(isUploading = false, progress = 0f) }
                }
            }
            .launchIn(viewModelScope)
    }

}