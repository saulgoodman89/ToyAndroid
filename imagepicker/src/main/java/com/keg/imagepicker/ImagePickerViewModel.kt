package com.keg.imagepicker

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ImagePickerViewModel : ViewModel() {
    private val _images = MutableStateFlow<List<Uri>>(emptyList())
    val images : StateFlow<List<Uri>> = _images

    fun addImages(newUris: List<Uri>) {
        _images.value = _images.value+newUris
    }
}