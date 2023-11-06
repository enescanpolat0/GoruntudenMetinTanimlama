package com.enescanpolat.goruntudenmetintanmlama.presentation

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enescanpolat.goruntudenmetintanmlama.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository:MainRepository
):ViewModel() {

    private var _extractedText = MutableStateFlow("")
    val extractedText = _extractedText.asStateFlow()


    fun getTextFromCapturedImage(bitmap: Bitmap){
        viewModelScope.launch {
            repository.getTextFromCapturedImage(bitmap = bitmap)
                .collect{
                    _extractedText.value=it
                }
        }
    }

    fun getTextFromSelectedImage(uri: Uri){
        viewModelScope.launch {
            repository.getTextFromSelectedImage(uri = uri)
                .collect{text->
                    _extractedText.value=text
                }
        }
    }

    fun copyTextToClipboard(){
        viewModelScope.launch {
            repository.copyTextToClipboard(_extractedText.value)
        }
    }

}