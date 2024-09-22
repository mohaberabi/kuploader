package com.mohaberabi.kdownloader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mohaberabi.kdownloader.data.AndroidFileReader
import com.mohaberabi.kdownloader.data.DefaultFileUplaoderRepository
import com.mohaberabi.kdownloader.data.HttpClientFactory
import com.mohaberabi.kdownloader.presentation.screen.FileUploaderScreen
import com.mohaberabi.kdownloader.presentation.viewmodel.UploadViewModel
import com.mohaberabi.kdownloader.ui.theme.KDownloaderTheme
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val fileReader =
            AndroidFileReader(
                context = applicationContext,
                ioDispatcher = Dispatchers.IO
            )
        val repository = DefaultFileUplaoderRepository(
            httpClient = HttpClientFactory.client,
            ioDispatcher = Dispatchers.IO
        )
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KDownloaderTheme {
                FileUploaderScreen(
                    viewModel = UploadViewModel(
                        fileReader = fileReader,
                        repository = repository
                    )
                )
            }

        }
    }
}


