package com.enescanpolat.goruntudenmetintanmlama.presentation

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainScreen(viewModel: MainScreenViewModel = hiltViewModel()) {





    val galleryLuncher = rememberLauncherForActivityResult(
        contract = GetContent(),
        onResult = { viewModel.getTextFromSelectedImage(it.let { it!! }) }

    )

    val cameraLauncherIntent = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { viewModel.getTextFromCapturedImage(it.let { it!! }) }
    )

    val exractedtext = viewModel.extractedText.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Card(
            elevation = CardDefaults.cardElevation(7.dp)
        ) {
            LazyColumn(modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth()) {
                item {
                    SelectionContainer {
                        Text(text = exractedtext, modifier = Modifier.padding(vertical = 3.dp, horizontal = 7.dp))
                    }
                }
            }
        }




        Button(
            onClick = viewModel::copyTextToClipboard,
            enabled = exractedtext.isNotBlank()
        ) {
            Text(text = "Copy entire text")
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

            Button(onClick = {
                cameraLauncherIntent.launch()
            }) {
                Text(text = "Start camera")
            }

            Button(onClick = { galleryLuncher.launch("image/*") }) {
                Text(text = "Choose from gallery")
            }


        }


    }

}