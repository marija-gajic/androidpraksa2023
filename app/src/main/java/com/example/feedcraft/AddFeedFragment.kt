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
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.feedcraft.Constants.Companion.REQUEST_CAMERA
import com.example.feedcraft.Constants.Companion.REQUEST_GALLERY
import com.example.feedcraft.databinding.FragmentAddFeedBinding


class AddFeedFragment : DialogFragment() {
    private var _binding: FragmentAddFeedBinding? = null
    private val binding get() = _binding!!

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
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(pickPhoto, Constants.REQUEST_GALLERY)
        }
        addCamera.setOnClickListener {
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePicture, Constants.REQUEST_CAMERA)
        }


    }

    @SuppressLint("RestrictedApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        when (requestCode) {
            REQUEST_CAMERA -> if (resultCode == RESULT_OK) {
                val photoOrigin = "camera"
                val extras: Bundle? = data?.extras
                val imageBitmap = extras?.get("data") as Bitmap?
                UIApplication.tempBitmap = imageBitmap!!
                startActivity(Intent(requireContext(), EditorActivity::class.java))
            }
            REQUEST_GALLERY -> if (resultCode == RESULT_OK) {
                val photoOrigin = "gallery"
                val selectedImageUri: Uri? = data?.data
                UIApplication.imageUri = selectedImageUri!!
                startActivity(Intent(requireContext(), EditorActivity::class.java))
            }

        }

        when(resultCode) {
            RESULT_CANCELED -> dismiss()
        }
    }
}

