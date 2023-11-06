package com.enescanpolat.goruntudenmetintanmlama.domain.repository

import android.graphics.Bitmap
import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface MainRepository {


    fun getTextFromCapturedImage(bitmap: Bitmap): Flow<String>

    fun getTextFromSelectedImage(uri: Uri): Flow<String>

    fun copyTextToClipboard(text:String)

}