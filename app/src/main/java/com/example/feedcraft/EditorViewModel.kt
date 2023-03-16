package com.example.feedcraft

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

//import kotlinx.coroutines.NonCancellable.message

class EditorViewModel : ViewModel() {

    val message: MutableLiveData<String> = MutableLiveData()

    val _edits: MutableLiveData<Edits> = MutableLiveData(Edits("", 0,0,0))
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
        return edits.value!!.caption
    }

    fun getBrightness(): Int {
        return edits.value!!.brightness
    }

    fun getSaturation(): Int {
        return edits.value!!.saturation
    }

    fun getContrast(): Int {
        return edits.value!!.contrast
    }



    fun saveBitmap(context: Context, bitmap: Bitmap): File {

        val filePath = context?.filesDir.toString() + File.separator + "saved_creations"
        val fileName ="creation_1.png"
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)

        val dir = File(filePath)

        if (!dir.exists()) {
            dir.mkdir()
        }

        val f = File(dir, fileName)
        f.createNewFile()

        val fo = FileOutputStream(f)
        fo.write(bytes.toByteArray())
        fo.close()

        return f
    }


}

