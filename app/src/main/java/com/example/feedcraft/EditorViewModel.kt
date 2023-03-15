package com.example.feedcraft

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditorViewModel : ViewModel() {

    val message: MutableLiveData<String> = MutableLiveData()

    val _edits: MutableLiveData<Edits> = MutableLiveData(Edits("", 1,1,1))
    val edits: LiveData<Edits>
        get() = _edits

    fun setAnotherValueToLiveData(messageToReplaceWith: String)
    {
        message.value = messageToReplaceWith
    }

    fun setCaption(caption: String) {
        val newEdits: Edits = edits.value!!
        newEdits.caption = caption
        _edits.value = newEdits
    }

    fun setBrightness(brightness: Int) {
        val newEdits: Edits = edits.value!!
        newEdits.brightness = brightness
        _edits.value = newEdits
    }

    fun setSaturation(saturation: Int) {
        val newEdits: Edits = edits.value!!
        newEdits.saturation = saturation
        _edits.value = newEdits
    }

    fun setContrast(contrast: Int) {
        val newEdits: Edits = edits.value!!
        newEdits.contrast = contrast
        _edits.value = newEdits
    }

    fun getCaption(): String {
        return edits.value?.caption.toString()
    }




}