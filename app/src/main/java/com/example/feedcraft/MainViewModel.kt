package com.example.feedcraft

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson

class MainViewModel: ViewModel() {

    fun getPhotosFromJson(context: Context): MutableList<EditedPhotoInformation> {
        val creationsSharedPrefs = context?.getSharedPreferences("creations", Context.MODE_PRIVATE)
        val counterSharedPrefs = context?.getSharedPreferences("config", Context.MODE_PRIVATE)
        var counterOfCreations = counterSharedPrefs?.getInt("counter", 0)
        val gson = Gson()
        val photoInformationFromJson: MutableList<EditedPhotoInformation> = mutableListOf()
        for (i in 0..counterOfCreations!!-1) {
            var json = creationsSharedPrefs?.getString("creation_$i", "")
            var obj: EditedPhotoInformation = gson.fromJson(json, EditedPhotoInformation::class.java)
            val caption = obj.caption
            val brightness = obj.brighness
            val saturation = obj.saturation
            val contrast = obj.contrast
            val imgName = obj.imgName
            val filterName = obj.filterName
            photoInformationFromJson.add(EditedPhotoInformation(caption,brightness,saturation,contrast,imgName,filterName))
        }
        return photoInformationFromJson
    }

    fun getPhotoInformationFromPosition(context: Context, position: Int): EditedPhotoInformation {
        val listOfPhotos = getPhotosFromJson(context)
        val obj = listOfPhotos[position]
        return obj
    }
}