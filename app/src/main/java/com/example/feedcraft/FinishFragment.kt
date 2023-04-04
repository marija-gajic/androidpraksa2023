package com.example.feedcraft

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.feedcraft.databinding.FragmentFinishBinding
import com.google.gson.Gson
import java.io.File


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

//        if(UIApplication.photoOrigin == "gallery")
//        {//gallery
//            val selectedImageFromGalleryUri = UIApplication.imageUri
//            Glide.with(requireActivity()).load(selectedImageFromGalleryUri).into(imgPreview)
//        }
//        else
//        {//camera
//            val tempBitmap = UIApplication.tempBitmap as Bitmap
//            imgPreview.setImageBitmap(UIApplication.tempBitmap)
//        }

        imgPreview.setImageBitmap(UIApplication.tempEditedPhoto)

        btnSchedule.setOnClickListener {
            val actionSchedule = FinishFragmentDirections.actionFinishFragmentToScheduleFragment()
            findNavController().navigate(actionSchedule)
        }
        btnSave.setOnClickListener {


            var resultBitmap = imgPreview.drawToBitmap()
            viewModel.setAnotherValueToLiveData("Saving...")

            if (UIApplication.nameOfEditingSavedPhoto != "") {
                val cropResult = viewModel.cropEdgesOfPhoto(requireContext(), resultBitmap)
                viewModel.overwritePreviewBitmap(
                    requireContext(),
                    UIApplication.nameOfEditingSavedPhoto,
                    cropResult
                )
                viewModel.overwriteSavedBitmap(
                    requireContext(),
                    UIApplication.nameOfEditingSavedPhoto,
                    cropResult
                )
                UIApplication.nameOfEditingSavedPhoto = ""
                viewModel.setAnotherValueToLiveData("Photo saved!")
                UIApplication.photoSaved = "saved"
            } else
            {

            val cropResult = viewModel.cropEdgesOfPhoto(requireContext(), resultBitmap)
            val currentTimestamp = viewModel.getTimestamp()
            viewModel.saveBitmap(requireContext(), cropResult, currentTimestamp)
            viewModel.savePreview(requireContext(), cropResult, currentTimestamp)
            viewModel.setAnotherValueToLiveData("Photo saved!")
            UIApplication.photoSaved = "saved"
            val selectedFilterName =
                viewModel.returnFilterNameFromPosition(UIApplication.lastFilterSelected)

            val caption = viewModel.getCaption()
            val brightness = viewModel.getBrightness()
            val saturation = viewModel.getSaturation()
            val contrast = viewModel.getContrast()
            val imgName = currentTimestamp
            val filterName = selectedFilterName


            val obj = EditedPhotoInformation(
                caption,
                brightness,
                saturation,
                contrast,
                imgName,
                filterName
            )
            val gson = Gson()
            val json = gson.toJson(obj)


            val sharePrefsConfig = requireActivity().getSharedPreferences("config", 0)
            val prefsConfig = sharePrefsConfig.edit()
            var counterOfCreations = sharePrefsConfig.getInt("counter", 0)
            val position = counterOfCreations


            val sharePrefsCreations = requireActivity().getSharedPreferences("creations", 0)
            val prefsEditor = sharePrefsCreations.edit()
            prefsEditor.putString("creation_$counterOfCreations", json)
                //prefsEditor.putString(imgName, json)

            prefsConfig.putInt("counter", ++counterOfCreations)

            prefsEditor.commit()
            prefsConfig.commit()
        }

//            if(UIApplication.photoOrigin == "gallery")
//            {//gallery
//                viewModel.setAnotherValueToLiveData("Saving...")
//                val selectedImageFromGalleryUri = UIApplication.imageUri
//                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageFromGalleryUri)
//                viewModel.saveBitmap(requireContext(), bitmap)
//                viewModel.savePreview(requireContext(), bitmap)
//                viewModel.setAnotherValueToLiveData("Photo saved!")
//                UIApplication.photoSaved = "saved"
//            }
//            else
//            {//camera
//                viewModel.setAnotherValueToLiveData("Saving...")
//                val tempBitmap = UIApplication.tempBitmap as Bitmap
//                viewModel.saveBitmap(requireContext(), tempBitmap) //(tempBitmap, context?.filesDir.toString() + File.separator + "saved_creations", "creation_1.png")
//                viewModel.savePreview(requireContext(), tempBitmap)
//                viewModel.setAnotherValueToLiveData("Photo saved!")
//                UIApplication.photoSaved = "saved"
//            }
        }

        btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            val filePath = context?.filesDir.toString() + File.separator + "saved_creations" + File.separator
//            val file = File(filePath, "creation_1.png")
//            val uri: Uri? = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID, file)
//            intent.putExtra(Intent.EXTRA_STREAM, uri)
            val bitmap = UIApplication.tempEditedPhoto
            val uri = bitmap?.let { it1 -> viewModel.bitmapToUri(requireContext(), it1) }
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