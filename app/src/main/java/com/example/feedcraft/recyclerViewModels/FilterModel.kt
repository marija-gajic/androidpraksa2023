package com.example.feedcraft.recyclerViewModels

import android.graphics.Bitmap

data class FilterModel(val name: String, val filterBitmap: Bitmap, var selected: Boolean = false) {


}