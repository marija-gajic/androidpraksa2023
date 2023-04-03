package com.example.feedcraft

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri

class UIApplication : Application(){

    companion object {
        var imageUri: Uri? = null
        var tempBitmap: Bitmap? = null
        var camBitmap: Bitmap? = null
        var appliedEditsBitmap: Bitmap? = null
        var photoOrigin: String = ""
        var photoSaved: String = ""
        var tempEditedPhoto: Bitmap? = null
        var addPhotoFlag: String = ""
        var lastFilterSelected: Int = 0
        var editExisting: Int = 0
        var currentPosition: Int = -1
        var nameOfEditingSavedPhoto = ""
        var camImgUri: Uri? = null
    }

    override fun onCreate() {
        super.onCreate()
    }

}