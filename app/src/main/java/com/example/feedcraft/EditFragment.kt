package com.example.feedcraft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.feedcraft.databinding.FragmentEditBinding

class EditFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)

        val imgEditor = binding.imgEditor

        if(UIApplication.imageUri != null)
        {//gallery
            val selectedImageFromGalleryUri = UIApplication.imageUri
            Glide.with(requireActivity()).load(selectedImageFromGalleryUri).into(imgEditor)
        }
        else
        {//camera
            imgEditor.setImageBitmap(UIApplication.tempBitmap)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val addCaption = binding.captionField
        val btnFinish = binding.btnFinishEditor
        val btnBack = binding.btnBackEditor
        val btnCaption = binding.contCaptionEditor
        val btnFilter = binding.contFilterEditor
        val btnBrightness = binding.contBrightnessEditor
        val btnSaturation = binding.contSaturationEditor
        val btnContrast = binding.contContrastEditor
        val seek = binding.seekBar
        val percent = binding.percent

        val currentProgress = seek.progress.toString() + "%"
        percent.text = currentProgress
        seek.isVisible = false
        percent.isVisible = false

        seek?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                percent.text = progress.toString() + "%"
            }

            override fun onStartTrackingTouch(seek: SeekBar) {

            }

            override fun onStopTrackingTouch(seek: SeekBar) {

            }
        })

        addCaption.setOnClickListener {
            seek.isVisible = false
            percent.isVisible = false
            val actionCaption = EditFragmentDirections.actionEditFragmentToAddCaptionFragment()
            findNavController().navigate(actionCaption)
        }
        btnFinish.setOnClickListener {
            val actionFinish = EditFragmentDirections.actionEditFragmentToFinishFragment()
            findNavController().navigate(actionFinish)
        }
        btnCaption.setOnClickListener {
            seek.isVisible = false
            percent.isVisible = false
            val actionCaption = EditFragmentDirections.actionEditFragmentToAddCaptionFragment()
            findNavController().navigate(actionCaption)
        }
        btnBack.setOnClickListener {
            requireActivity().finish()
        }
        btnFilter.setOnClickListener {
            seek.isVisible = false
            percent.isVisible = false
        }
        btnBrightness.setOnClickListener {
            seek.isVisible = true
            percent.isVisible = true
        }
        btnSaturation.setOnClickListener {
            seek.isVisible = true
            percent.isVisible = true
        }
        btnContrast.setOnClickListener {
            seek.isVisible = true
            percent.isVisible = true
        }
    }



}