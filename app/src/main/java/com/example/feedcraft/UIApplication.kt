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
    }

    override fun onCreate() {
        super.onCreate()
    }

}