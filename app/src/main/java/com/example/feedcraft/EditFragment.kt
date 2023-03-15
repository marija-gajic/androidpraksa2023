package com.example.feedcraft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.feedcraft.databinding.FragmentEditBinding


class EditFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)

        val imgEditor = binding.imgEditor

        if(UIApplication.photoOrigin == "gallery")
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

        //viewModel = ViewModelProvider(requireActivity())[EditorViewModel::class.java]
        viewModel!!.message.observe(viewLifecycleOwner) { newMessage ->
            Toast.makeText(requireContext(), "Stigla je poruka:" + newMessage, Toast.LENGTH_SHORT).show()
        }

        val txtCaption = viewModel.getCaption()
        addCaption.text = txtCaption


        val currentProgress = seek.progress.toString() + "%"
        percent.text = currentProgress
        setSeekPercentVisible(false)

        seek?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                percent.text = progress.toString() + "%"
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {
                val prog = seek.progress
                viewModel.setBrightness(prog)

            }
        })



        addCaption.setOnClickListener {
            setSeekPercentVisible(false)
            val actionCaption = EditFragmentDirections.actionEditFragmentToAddCaptionFragment()
            findNavController().navigate(actionCaption)
        }
        btnFinish.setOnClickListener {
            val actionFinish = EditFragmentDirections.actionEditFragmentToFinishFragment()
            findNavController().navigate(actionFinish)

        }
        btnCaption.setOnClickListener {
            setSeekPercentVisible(false)
            val actionCaption = EditFragmentDirections.actionEditFragmentToAddCaptionFragment()
            findNavController().navigate(actionCaption)
        }
        btnBack.setOnClickListener {
            requireActivity().finish()
        }
        btnFilter.setOnClickListener {
            setSeekPercentVisible(false)
        }
        btnBrightness.setOnClickListener {
            setSeekPercentVisible(true)



        }
        btnSaturation.setOnClickListener {
            setSeekPercentVisible(true)


        }
        btnContrast.setOnClickListener {
            setSeekPercentVisible(true)
            //viewModel?.setAnotherValueToLiveData("Nova poruka")

        }


    }

    private fun setSeekPercentVisible(isVisible: Boolean){
        val seek = binding.seekBar
        val percent = binding.percent
        seek.isVisible = isVisible
        percent.isVisible = isVisible
    }



}