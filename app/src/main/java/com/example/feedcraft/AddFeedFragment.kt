package com.example.feedcraft

import android.annotation.SuppressLint
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.feedcraft.Constants.Companion.REQUEST_CAMERA
import com.example.feedcraft.Constants.Companion.REQUEST_GALLERY
import com.example.feedcraft.databinding.FragmentAddFeedBinding
import java.io.File


class AddFeedFragment : DialogFragment() {
    private var _binding: FragmentAddFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var capturedImageUri: Uri
    private val viewModel: EditorViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val addGallery = binding.btnGallery
        val addCamera = binding.btnCamera

        addGallery.setOnClickListener {
            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(pickPhoto, Constants.REQUEST_GALLERY)
        }
        addCamera.setOnClickListener {
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            val file = File(context?.cacheDir.toString() + File.separator + "capturedImage.png")
            capturedImageUri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID, file)
            //UIApplication.camImgUri = capturedImageUri
            takePicture.putExtra(Intent.EXTRA_STREAM, capturedImageUri)
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri)
            startActivityForResult(takePicture, Constants.REQUEST_CAMERA)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CAMERA -> if (resultCode == RESULT_OK) {
                UIApplication.photoOrigin = "camera"

//                val extras: Bundle? = data?.extras
//                val imageBitmap = extras?.get("data") as Bitmap?
//                UIApplication.camBitmap = imageBitmap!!

                UIApplication.camBitmap = viewModel.uriToBitmap(requireContext(), capturedImageUri)
                findNavController().navigateUp()
                startActivity(Intent(requireContext(), EditorActivity::class.java))
//                Glide.with(this)
//                    .asBitmap()
//                    .override((0.9f * resources.displayMetrics.widthPixels).toInt())
//                    .load(capturedImageUri)
//                    .into(object : CustomTarget<Bitmap>(){
//                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//
//
//                            UIApplication.camBitmap = resource
//                            findNavController().navigateUp()
//                            startActivity(Intent(requireContext(), EditorActivity::class.java))
//
//                        }
//                        override fun onLoadCleared(placeholder: Drawable?) {
//
//                        }
//                    })

            }
            REQUEST_GALLERY -> if (resultCode == RESULT_OK) {
                UIApplication.photoOrigin = "gallery"
                val selectedImageUri: Uri? = data?.data
                UIApplication.imageUri = selectedImageUri!!
                findNavController().navigateUp()
                startActivity(Intent(requireContext(), EditorActivity::class.java))
            }

        }

        when(resultCode) {
            RESULT_CANCELED -> findNavController().navigateUp()
        }
    }
}

