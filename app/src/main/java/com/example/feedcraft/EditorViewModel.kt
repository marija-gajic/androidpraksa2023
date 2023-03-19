package com.example.feedcraft

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


//import kotlinx.coroutines.NonCancellable.message

class EditorViewModel : ViewModel() {
    private var filterList : MutableList<FilterModel> = mutableListOf()
    private lateinit var filterAdapter: FilterAdapter

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

    private fun getTimestamp() = System.currentTimeMillis().toString()

    fun saveBitmap(context: Context, bitmap: Bitmap): File {

        val filePath = context.filesDir.toString() + File.separator + "saved_creations"
        val fileName ="creation_1.png"
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 99, bytes)

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

    fun savePreview(context: Context, bitmap: Bitmap): File {

        val filePath = context.filesDir.toString() + File.separator + "creations_preview"
        val fileName = getTimestamp() + ".png"
        val bytes = ByteArrayOutputStream()
        val scaledCroppedBitmap = scaleCenterCrop(bitmap,200,200)
//        Glide.with(context)
//            .asBitmap()
//            .load(thisBitmap)
//            .into(object: CustomTarget<Bitmap>(200,200) {
//                override fun onLoadCleared(placeholder: Drawable?) {
//                }
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                }
//            })
        //val thisbitmap = Bitmap.createScaledBitmap(bitmap, 200,200, true)
        scaledCroppedBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)


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

    fun scaleCenterCrop(bitmap: Bitmap, newHeight: Int, newWidth: Int): Bitmap {
        val sourceWidth: Int = bitmap.width
        val sourceHeight: Int = bitmap.height

        val xScale = newWidth.toFloat() / sourceWidth
        val yScale = newHeight.toFloat() / sourceHeight
        val scale = Math.max(xScale, yScale)

        val scaledWidth = scale * sourceWidth
        val scaledHeight = scale * sourceHeight

        val left = (newWidth - scaledWidth) / 2
        val top = (newHeight - scaledHeight) / 2

        val targetRect = RectF(left, top, left + scaledWidth, top + scaledHeight)

        val dest = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig())
        val canvas = Canvas(dest)
        canvas.drawBitmap(bitmap, null, targetRect, null)

        return dest
    }

    fun preparePreviewBitmap(context: Context): Bitmap {
        var previewBitmap: Bitmap
        if (UIApplication.photoOrigin == "gallery") {
            val selectedImageFromGalleryUri = UIApplication.imageUri
            previewBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, selectedImageFromGalleryUri)
        } else {
            previewBitmap = UIApplication.tempBitmap!!
        }
        previewBitmap = scaleCenterCrop(previewBitmap, 200, 200)
        return previewBitmap
    }

    fun getListOfPreviewsFromStorage(context: Context): MutableList<Bitmap> {
        val folderPath = context.filesDir.toString() + File.separator + "creations_preview"
        //val files: Array<String> = context.fileList()
        val previewList: MutableList<Bitmap> = mutableListOf()

        File(folderPath).walk().forEach { file ->
            if (!file.isDirectory) {
                val tmpBitmap = BitmapFactory.decodeFile(file.absolutePath)
                previewList.add(tmpBitmap)
            }
        }
        return previewList
    }




}

