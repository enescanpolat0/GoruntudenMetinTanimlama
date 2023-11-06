package com.enescanpolat.goruntudenmetintanmlama.data.repository

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.widget.Toast
import com.enescanpolat.goruntudenmetintanmlama.domain.repository.MainRepository
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val context: Context,
    private val recognizer:TextRecognizer,
    private val clipboardManager: ClipboardManager
) :MainRepository {


    override fun getTextFromCapturedImage(bitmap: Bitmap): Flow<String> {
        return callbackFlow {
            val inputImage = InputImage.fromBitmap(bitmap,0)
            recognizer.process(inputImage)
                .addOnSuccessListener {
                    launch {
                        send(it.text)
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }

            awaitClose {  }
        }
    }

    override fun getTextFromSelectedImage(uri: Uri): Flow<String> {
        return callbackFlow {
            val inputImage = InputImage.fromFilePath(context,uri)
            recognizer.process(inputImage)
                .addOnSuccessListener {
                    launch {
                        send(it.text)
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }

            awaitClose {  }
        }
    }

    override fun copyTextToClipboard(text: String) {
        clipboardManager.setPrimaryClip(ClipData.newPlainText("",text))
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
            Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
    }
}