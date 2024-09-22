package com.mohaberabi.kdownloader.presentation.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.mohaberabi.kdownloader.presentation.viewmodel.UploadViewModel


@Composable
fun FileUploaderScreen(
    modifier: Modifier = Modifier,
    viewModel: UploadViewModel
) {
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
        ) { uri ->
            uri?.let {
                viewModel.pickFileAndUpload(it.toString())
            }
        }


    val state by viewModel.state.collectAsState()



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (state.isUploading) {
            UploadingCompose(progress = state.progress)
        } else {
            Button(
                onClick = { launcher.launch("*/*") },
            ) {
                Text(text = "Pickup file to upload")
            }
        }

    }


}

@Composable
fun UploadingCompose(
    modifier: Modifier = Modifier,
    progress: Float
) {

    Column {

        LinearProgressIndicator(progress = { progress })

        Text("${progress * 100}%")
    }


}