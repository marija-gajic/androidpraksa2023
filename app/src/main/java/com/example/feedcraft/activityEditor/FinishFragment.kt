package com.example.feedcraft.activityEditor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
//import com.example.feedcraft.FinishFragmentDirections
import com.example.feedcraft.data.EditedPhotoInformation
import com.example.feedcraft.data.UIApplication
import com.example.feedcraft.databinding.FragmentFinishBinding
import com.example.feedcraft.viewModels.EditorViewModel
import com.google.gson.Gson


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

        imgPreview.setImageBitmap(UIApplication.tempEditedPhoto)

        btnSchedule.setOnClickListener {
            val actionSchedule = FinishFragmentDirections.actionFinishFragmentToScheduleFragment()
            findNavController().navigate(actionSchedule)
        }
        btnSave.setOnClickListener {


            var resultBitmap = imgPreview.drawToBitmap()
            viewModel.setAnotherValueToLiveData("Saving...")

            if (UIApplication.nameOfEditingSavedPhoto != "") {
                viewModel.overwritePreviewBitmap(
                    requireContext(),
                    UIApplication.nameOfEditingSavedPhoto,
                    resultBitmap
                )
                viewModel.overwriteSavedBitmap(
                    requireContext(),
                    UIApplication.nameOfEditingSavedPhoto,
                    resultBitmap
                )
                UIApplication.nameOfEditingSavedPhoto = ""
                viewModel.setAnotherValueToLiveData("Photo saved!")
                UIApplication.photoSaved = "saved"
            } else
            {
            val currentTimestamp = viewModel.getTimestamp()
            viewModel.saveBitmap(requireContext(), resultBitmap, currentTimestamp)
            viewModel.savePreview(requireContext(), resultBitmap, currentTimestamp)
            viewModel.setAnotherValueToLiveData("Photo saved!")
            UIApplication.photoSaved = "saved"
            val selectedFilterName = viewModel.returnFilterNameFromPosition(UIApplication.lastFilterSelected)

            val caption = viewModel.getCaption()
            val brightness = viewModel.getBrightness()
            val saturation = viewModel.getSaturation()
            val contrast = viewModel.getContrast()
            val imgName = currentTimestamp
            val filterName = selectedFilterName

            val sharePrefsConfig = requireActivity().getSharedPreferences("config", 0)
            val prefsConfig = sharePrefsConfig.edit()
            var counterOfCreations = sharePrefsConfig.getInt("counter", 0)

                val key = "creation_$counterOfCreations"

                val obj = EditedPhotoInformation(
                    key,
                    caption,
                    brightness,
                    saturation,
                    contrast,
                    imgName,
                    filterName
                )
                val gson = Gson()
                val json = gson.toJson(obj)


            val sharePrefsCreations = requireActivity().getSharedPreferences("creations", 0)
            val prefsEditor = sharePrefsCreations.edit()
            prefsEditor.putString("creation_$counterOfCreations", json)

            prefsConfig.putInt("counter", ++counterOfCreations)

            prefsEditor.commit()
            prefsConfig.commit()
        }
        }

        btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
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