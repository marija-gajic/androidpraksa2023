package com.example.feedcraft

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.feedcraft.databinding.FragmentFinishBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class FinishFragment : Fragment() {
    private var _binding: FragmentFinishBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditorViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnShare = binding.btnShareFinish
        val btnSchedule = binding.btnScheduleFinish
        val btnSave = binding.btnSaveFinish
        val btnDiscard = binding.btnDiscardFinish
        val imgPreview = binding.imgPreview
        val captionField = binding.captionFieldFinish

        captionField.text = viewModel.getCaption()

        viewModel!!.message.observe(viewLifecycleOwner) { newMessage ->
            Toast.makeText(requireContext(), newMessage, Toast.LENGTH_SHORT).show()
        }

        if(UIApplication.photoOrigin == "gallery")
        {//gallery
            val selectedImageFromGalleryUri = UIApplication.imageUri
            Glide.with(requireActivity()).load(selectedImageFromGalleryUri).into(imgPreview)
        }
        else
        {//camera
            val tempBitmap = UIApplication.tempBitmap as Bitmap
            imgPreview.setImageBitmap(UIApplication.tempBitmap)
        }

        btnSchedule.setOnClickListener {
            val actionSchedule = FinishFragmentDirections.actionFinishFragmentToScheduleFragment()
            findNavController().navigate(actionSchedule)
        }
        btnSave.setOnClickListener {

            if(UIApplication.photoOrigin == "gallery")
            {//gallery
                viewModel.setAnotherValueToLiveData("Saving...")
                val selectedImageFromGalleryUri = UIApplication.imageUri
                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageFromGalleryUri)
                viewModel.saveBitmap(requireContext(), bitmap)
                viewModel.savePreview(requireContext(), bitmap)
                viewModel.setAnotherValueToLiveData("Photo saved!")
                UIApplication.photoSaved = "saved"
            }
            else
            {//camera
                viewModel.setAnotherValueToLiveData("Saving...")
                val tempBitmap = UIApplication.tempBitmap as Bitmap
                viewModel.saveBitmap(requireContext(), tempBitmap) //(tempBitmap, context?.filesDir.toString() + File.separator + "saved_creations", "creation_1.png")
                viewModel.savePreview(requireContext(), tempBitmap)
                viewModel.setAnotherValueToLiveData("Photo saved!")
                UIApplication.photoSaved = "saved"
            }
        }

        btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val filePath = context?.filesDir.toString() + File.separator + "saved_creations" + File.separator
            val file = File(filePath, "creation_1.png")
            val uri: Uri? = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID, file)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.type = "image/png"
            val pm = requireActivity().packageManager
            if (intent.resolveActivity(pm) != null) {
                startActivity(intent)
            }
        }

        btnDiscard.setOnClickListener {
            val actionDiscard = FinishFragmentDirections.actionFinishFragmentToEditFragment()
            findNavController().navigate(actionDiscard)
            requireActivity().setResult(1005)

        }

    }

}