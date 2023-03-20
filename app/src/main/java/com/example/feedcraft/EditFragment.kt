package com.example.feedcraft

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.feedcraft.databinding.FragmentEditBinding


class EditFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditorViewModel by activityViewModels()
    private var brightnessClicked: Boolean = false
    private var saturationClicked: Boolean = false
    private var contrastClicked: Boolean = false
    private var filterList : MutableList<FilterModel> = mutableListOf()
    private lateinit var filterAdapter: FilterAdapter

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

    @SuppressLint("ResourceAsColor")
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
        val filters = binding.rvFilter

        filters.isVisible = false

        viewModel.edits.observe(viewLifecycleOwner) { newEdit ->
            addCaption.setText(newEdit.caption)
        }

        addCaption.setText(viewModel.edits.value?.caption)

        val currentProgress = seek.progress.toString() + "%"
        percent.text = currentProgress
        setSeekPercentVisible(false)

        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                percent.text = progress.toString() + "%"
            }

            override fun onStartTrackingTouch(seek: SeekBar) {}

            override fun onStopTrackingTouch(seek: SeekBar) {

                if (brightnessClicked){
                    viewModel.setBrightness(seek.progress)
                }
                if (saturationClicked){
                    viewModel.setSaturation(seek.progress)
                }
                if (contrastClicked){
                    viewModel.setContrast(seek.progress)
                }

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
            filters.isVisible = false
            val actionCaption = EditFragmentDirections.actionEditFragmentToAddCaptionFragment()
            findNavController().navigate(actionCaption)
        }
        btnBack.setOnClickListener {
            requireActivity().finish()
        }
        btnFilter.setOnClickListener {
            setSeekPercentVisible(false)
            filters.isVisible = true

        }
        btnBrightness.setOnClickListener {
            setSeekPercentVisible(true)
            brightnessClicked = true
            saturationClicked = false
            contrastClicked = false
            filters.isVisible = false
            val brightnessAmount = viewModel.getBrightness()
            seek.progress = brightnessAmount
            percent.text = brightnessAmount.toString() + "%"
        }
        btnSaturation.setOnClickListener {
            setSeekPercentVisible(true)
            brightnessClicked = false
            saturationClicked = true
            contrastClicked = false
            filters.isVisible = false
            val saturationAmount = viewModel.getSaturation()
            seek.progress = saturationAmount
            percent.text = saturationAmount.toString() + "%"
        }
        btnContrast.setOnClickListener {
            setSeekPercentVisible(true)
            brightnessClicked = false
            saturationClicked = false
            contrastClicked = true
            filters.isVisible = false
            val contrastAmount = viewModel.getContrast()
            seek.progress = contrastAmount
            percent.text = contrastAmount.toString() + "%"
        }

        loadData()
        filterAdapter = FilterAdapter(filterList) {
            Log.d("mylog", "$it")
        }
        binding.rvFilter.adapter = filterAdapter
    }


    fun loadData() {
        val previewBitmap = viewModel.preparePreviewBitmap(requireContext())
        filterList.add(FilterModel("Sample 1", previewBitmap))
        filterList.add(FilterModel("Sample 2", previewBitmap))
        filterList.add(FilterModel("Sample 3", previewBitmap))
        filterList.add(FilterModel("Sample 4", previewBitmap))
        filterList.add(FilterModel("Sample 5", previewBitmap))
        filterList.add(FilterModel("Sample 6", previewBitmap))
        filterList.add(FilterModel("Sample 7", previewBitmap))
        filterList.add(FilterModel("Sample 8", previewBitmap))
        filterList.add(FilterModel("Sample 9", previewBitmap))
    }

    private fun setSeekPercentVisible(isVisible: Boolean){
        val seek = binding.seekBar
        val percent = binding.percent
        seek.isVisible = isVisible
        percent.isVisible = isVisible
    }




}