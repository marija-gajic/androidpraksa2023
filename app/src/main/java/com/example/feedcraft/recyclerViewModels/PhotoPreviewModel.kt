package com.example.feedcraft.recyclerViewModels

import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes

data class PhotoPreviewModel(val previewBitmap: Bitmap, @ColorInt var borderColor: Int) {


}