package com.example.feedcraft

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.navigation.fragment.findNavController
import com.example.feedcraft.databinding.FragmentEditBinding
import com.example.feedcraft.databinding.FragmentFeedBinding

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



        addCaption.setOnClickListener {
            val actionCaption = EditFragmentDirections.actionEditFragmentToAddCaptionFragment()
            findNavController().navigate(actionCaption)
        }
        btnFinish.setOnClickListener {
            val actionFinish = EditFragmentDirections.actionEditFragmentToFinishFragment()
            findNavController().navigate(actionFinish)
        }
        btnCaption.setOnClickListener {
            val actionCaption = EditFragmentDirections.actionEditFragmentToAddCaptionFragment()
            findNavController().navigate(actionCaption)
        }
        btnBack.setOnClickListener {
            requireActivity().finish()
        }
        btnFilter.setOnClickListener {
            //TODO
        }
        btnBrightness.setOnClickListener {
            //TODO
        }
        btnSaturation.setOnClickListener {
            //TODO
        }
        btnContrast.setOnClickListener {
            //TODO
        }
    }



}