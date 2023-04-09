package com.example.feedcraft.activityMain

import android.annotation.SuppressLint
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.feedcraft.BuildConfig
import com.example.feedcraft.activityEditor.EditorActivity
import com.example.feedcraft.data.Constants
import com.example.feedcraft.data.Constants.Companion.REQUEST_CAMERA
import com.example.feedcraft.data.Constants.Companion.REQUEST_GALLERY
import com.example.feedcraft.data.UIApplication
import com.example.feedcraft.databinding.FragmentAddFeedBinding
import com.example.feedcraft.viewModels.EditorViewModel
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
            capturedImageUri = FileProvider.getUriForFile(requireContext(),
                BuildConfig.APPLICATION_ID, file)
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
                UIApplication.camBitmap = viewModel.uriToBitmap(requireContext(), capturedImageUri)
                findNavController().navigateUp()
                startActivity(Intent(requireContext(), EditorActivity::class.java))
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

