package com.example.feedcraft

import android.R.attr.bitmap
import android.R.attr.orientation
import android.content.Context
import android.graphics.*
import android.media.ExifInterface
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.*
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

     fun getTimestamp() = System.currentTimeMillis().toString()

    fun saveBitmap(context: Context, bitmap: Bitmap): File {

        val filePath = context.filesDir.toString() + File.separator + "saved_creations"
        val fileName = getTimestamp() + ".png"
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

                var tmpBitmap = BitmapFactory.decodeFile(file.absolutePath)
                previewList.add(tmpBitmap)

               /* val exif = ExifInterface(file.absolutePath)
                val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)
                Log.e("orientation", "" + orientation)
                val m = Matrix()
                when (orientation) {
                    3 -> {
                        m.postRotate(180f)
                        //               if(m.preRotate(90)){
                        Log.e("in orientation", "" + orientation)
                        tmpBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.width, tmpBitmap.height, m, true)
                        previewList.add(tmpBitmap)
                    }
                    6 -> {
                        m.postRotate(90f)
                        Log.e("in orientation", "" + orientation)
                        tmpBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.width, tmpBitmap.height, m, true)
                        previewList.add(tmpBitmap)
                    }
                    8 -> {
                        m.postRotate(270f)
                        Log.e("in orientation", "" + orientation)
                        tmpBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0, tmpBitmap.width, tmpBitmap.height, m, true)
                        previewList.add(tmpBitmap)
                    }
                    else -> previewList.add(tmpBitmap)
                }*/


            }
        }
        return previewList
    }


    fun getBitmapFromInternalStorageByPosition(context: Context, position: Int): Bitmap {
        val folderPath = context.filesDir.toString() + File.separator + "saved_creations"
        val previewList: MutableList<Bitmap> = mutableListOf()

        File(folderPath).walk().forEach { file ->
            if (!file.isDirectory) {
                val tmpBitmap = BitmapFactory.decodeFile(file.absolutePath)
                previewList.add(tmpBitmap)
            }
        }
        return previewList[position]
    }

    fun deleteBitmapFromInternalStorageByPosition(context: Context, position: Int): Unit {
        val folderPathSaved = context.filesDir.toString() + File.separator + "saved_creations"
        //val previewListSaved: MutableList<Bitmap> = mutableListOf()
        var countSaved = 0

        File(folderPathSaved).walk().forEach { file ->
            if (!file.isDirectory) {
                if(countSaved==position) {
                    file.delete()
                } else {
                    countSaved++
                }
                //val tmpBitmap = BitmapFactory.decodeFile(file.absolutePath)
                //previewListSaved.add(tmpBitmap)

            }
        }
        //previewListSaved.removeAt(position)

        val folderPathPreviews = context.filesDir.toString() + File.separator + "creations_preview"
        //val previewListPreviews: MutableList<Bitmap> = mutableListOf()
        var countPreviews = 0

        File(folderPathPreviews).walk().forEach { file ->
            if (!file.isDirectory) {
                if(countPreviews==position) {
                    file.delete()
                } else {
                    countPreviews++
                }
//                val tmpBitmap = BitmapFactory.decodeFile(file.absolutePath)
//                previewListPreviews.add(tmpBitmap)
            }
        }
        //previewListPreviews.removeAt(position)



    }

    fun returnBitmapListWithFiltersApplied(context: Context, bitmap: Bitmap): MutableList<FilterModel> {
        val filteredList: MutableList<FilterModel> = mutableListOf()
        val gpuImage = GPUImage(context)
        gpuImage.setImage(bitmap)
        gpuImage.setFilter(GPUImageSepiaToneFilter())
        val filteredImageSepia: Bitmap = gpuImage.bitmapWithFilterApplied

        gpuImage.setFilter(GPUImageHueFilter())
        val filteredImageHue: Bitmap = gpuImage.bitmapWithFilterApplied

        gpuImage.setFilter(GPUImageVignetteFilter())
        val filteredImageVignette: Bitmap = gpuImage.bitmapWithFilterApplied

        gpuImage.setFilter(GPUImageMonochromeFilter())
        val filteredImageMonochrome: Bitmap = gpuImage.bitmapWithFilterApplied

        gpuImage.setFilter(GPUImageHazeFilter())
        val filteredImageHaze: Bitmap = gpuImage.bitmapWithFilterApplied

        gpuImage.setFilter(GPUImageColorBalanceFilter())
        val filteredImageColorBalance: Bitmap = gpuImage.bitmapWithFilterApplied

        gpuImage.setFilter(GPUImageEmbossFilter())
        val filteredImageEmboss: Bitmap = gpuImage.bitmapWithFilterApplied

        gpuImage.setFilter(GPUImageGaussianBlurFilter())
        val filteredImageBlur: Bitmap = gpuImage.bitmapWithFilterApplied



        filteredList.add(FilterModel("Normal", bitmap))
        filteredList.add(FilterModel("Sepia", filteredImageSepia))
        filteredList.add(FilterModel("Hue", filteredImageHue))
        filteredList.add(FilterModel("Vignette", filteredImageVignette))
        filteredList.add(FilterModel("Monochrome", filteredImageMonochrome))
        filteredList.add(FilterModel("Haze", filteredImageHaze))
        filteredList.add(FilterModel("Color Balance", filteredImageColorBalance))
        filteredList.add(FilterModel("Emboss", filteredImageEmboss))
        filteredList.add(FilterModel("Blur", filteredImageBlur))

        return filteredList
    }

    fun applyFilterOnBitmapFromPosition(context: Context, bitmap: Bitmap, position: Int): Bitmap {
        val gpuImage = GPUImage(context)
        gpuImage.setImage(bitmap)
        lateinit var filteredBitmap: Bitmap
        when(position) {
            0 -> {
                filteredBitmap = bitmap
            }
            1 -> {
                gpuImage.setFilter(GPUImageSepiaToneFilter())
                filteredBitmap = gpuImage.bitmapWithFilterApplied
            }
            2 -> {
                gpuImage.setFilter(GPUImageHueFilter())
                filteredBitmap = gpuImage.bitmapWithFilterApplied
            }
            3 -> {
                gpuImage.setFilter(GPUImageVignetteFilter())
                filteredBitmap = gpuImage.bitmapWithFilterApplied
            }
            4 -> {
                gpuImage.setFilter(GPUImageMonochromeFilter())
                filteredBitmap = gpuImage.bitmapWithFilterApplied
            }
            5 -> {
                gpuImage.setFilter(GPUImageHazeFilter())
                filteredBitmap = gpuImage.bitmapWithFilterApplied
            }
            6 -> {
                gpuImage.setFilter(GPUImageColorBalanceFilter())
                filteredBitmap = gpuImage.bitmapWithFilterApplied
            }
            7 -> {
                gpuImage.setFilter(GPUImageEmbossFilter())
                filteredBitmap = gpuImage.bitmapWithFilterApplied
            }
            8 -> {
                gpuImage.setFilter(GPUImageGaussianBlurFilter())
                filteredBitmap = gpuImage.bitmapWithFilterApplied
            }

        }
        return filteredBitmap
    }

    fun applyBrightness(bitmap: Bitmap, brightness: Int): Bitmap {
        val brightnessLevel = brightness.toFloat() / 100f * 255f
        val colorMatrix = ColorMatrix()
        colorMatrix.set(
            floatArrayOf(
                1f, 0f, 0f, 0f, brightnessLevel,
                0f, 1f, 0f, 0f, brightnessLevel,
                0f, 0f, 1f, 0f, brightnessLevel,
                0f, 0f, 0f, 1f, 0f
            )
        )
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        val adjustedBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val canvas = Canvas(adjustedBitmap)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return adjustedBitmap
    }

    fun applyContrast(bitmap: Bitmap, contrast: Int): Bitmap {
        val contrastLevel = (contrast.toFloat() / 100f + 1f) * 127f
        val colorMatrix = ColorMatrix()
        colorMatrix.set(
            floatArrayOf(
                contrastLevel / 127f, 0f, 0f, 0f, 0f,
                0f, contrastLevel / 127f, 0f, 0f, 0f,
                0f, 0f, contrastLevel / 127f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        val adjustedBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val canvas = Canvas(adjustedBitmap)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return adjustedBitmap
    }

    fun applySaturation(bitmap: Bitmap, saturation: Int): Bitmap {
        val saturationLevel = saturation.toFloat() / 100f
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(saturationLevel)
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        val adjustedBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val canvas = Canvas(adjustedBitmap)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return adjustedBitmap
    }

    fun cropEdgesOfPhoto (context: Context, bitmap: Bitmap): Bitmap {
        val croppedRect = Rect(10, 10, bitmap.width - 10, bitmap.height - 10)
        return Bitmap.createBitmap(bitmap, croppedRect.left, croppedRect.top, croppedRect.width(), croppedRect.height())
    }






}

