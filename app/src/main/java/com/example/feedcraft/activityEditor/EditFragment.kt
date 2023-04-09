package com.example.feedcraft.activityEditor

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
//import com.example.feedcraft.EditFragmentDirections
import com.example.feedcraft.data.UIApplication
import com.example.feedcraft.databinding.FragmentEditBinding
import com.example.feedcraft.recyclerViewAdapters.FilterAdapter
import com.example.feedcraft.recyclerViewModels.FilterModel
import com.example.feedcraft.viewModels.EditorViewModel
import com.example.feedcraft.viewModels.MainViewModel


class EditFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditorViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var brightnessClicked: Boolean = false
    private var saturationClicked: Boolean = false
    private var contrastClicked: Boolean = false
    private var filterList : MutableList<FilterModel> = mutableListOf()
    private lateinit var filterAdapter: FilterAdapter
    lateinit var filterSelected: FilterModel
    lateinit var filterBitmap: Bitmap
//    var sentFromFeedCode = 0
//    var positionFromFeed = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)

//        val bundle = arguments
//        if (bundle != null) {
//            val positionFromFeed = bundle?.getInt("position", 111)!!
//        }


//        if(sentFromFeedCode == 1005) {
//            val bitmapFromFeed = viewModel.getBitmapFromInternalStorageByPosition(requireContext(),positionFromFeed)
//            val imgEditor = binding.imgEditor
//            imgEditor.setImageBitmap(bitmapFromFeed)
//        } else {
            val imgEditor = binding.imgEditor
            //imgEditor.setImageBitmap(UIApplication.tempEditedPhoto)

            if(UIApplication.photoOrigin == "gallery")
            {//gallery
                val selectedImageFromGalleryUri = UIApplication.imageUri
                Glide.with(requireActivity()).load(selectedImageFromGalleryUri).into(imgEditor)
                val uritobitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageFromGalleryUri)
                UIApplication.tempEditedPhoto = uritobitmap
            }
        if(UIApplication.photoOrigin == "camera")
            {//camera
                //val bitmapTemp = UIApplication.camBitmap
                val bitmapTemp = viewModel.rotatePhotoIfNeeded(requireContext(), UIApplication.camBitmap!!)
                UIApplication.tempBitmap = bitmapTemp
                UIApplication.tempEditedPhoto = bitmapTemp
                imgEditor.setImageBitmap(bitmapTemp)
            }

        if(UIApplication.editExisting == 1) {
            var position = UIApplication.currentPosition
            val obj = mainViewModel.getPhotoInformationFromPosition(requireContext(),position)
            val imgName = obj.imgName
            val imgBitmap = viewModel.getBitmapFromInternalStorageByName(requireContext(), imgName)
            UIApplication.tempEditedPhoto = imgBitmap
            UIApplication.tempBitmap = imgBitmap
            UIApplication.nameOfEditingSavedPhoto = imgName
            imgEditor.setImageBitmap(imgBitmap)//
        }
//        }



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

                if (brightnessClicked){
                    var adjustedBitmap = viewModel.applyBrightness(UIApplication.tempEditedPhoto!!, progress)
                    //var rotatedBitmap = viewModel.rotatePhotoIfNeeded(requireContext(), adjustedBitmap)
                    binding.imgEditor.setImageBitmap(adjustedBitmap)
                }
                if (saturationClicked){
                    var adjustedBitmap = viewModel.applySaturation(UIApplication.tempEditedPhoto!!, progress)
                    binding.imgEditor.setImageBitmap(adjustedBitmap)
                }
                if (contrastClicked){
                    var adjustedBitmap = viewModel.applyContrast(UIApplication.tempEditedPhoto!!, progress)
                    binding.imgEditor.setImageBitmap(adjustedBitmap)
                }


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

                UIApplication.tempEditedPhoto = binding.imgEditor.drawToBitmap()


            }
        })

        addCaption.setOnClickListener {
            setSeekPercentVisible(false)
            val actionCaption = EditFragmentDirections.actionEditFragmentToAddCaptionFragment()
            findNavController().navigate(actionCaption)
        }
        btnFinish.setOnClickListener {
            UIApplication.tempEditedPhoto = binding.imgEditor.drawToBitmap()
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
        filterAdapter = FilterAdapter(filterList) {position ->
            Log.d("mylog", "$position")
            UIApplication.lastFilterSelected = position
            filterSelected = filterList[position]
            //filterBitmap = filterSelected.filterBitmap
            //filterBitmap = viewModel.getBitmapFromInternalStorageByPosition(requireContext(),position)
            if (UIApplication.photoOrigin == "gallery") {
                val selectedImageFromGalleryUri = UIApplication.imageUri
                val uritobitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageFromGalleryUri)
                //val rotatedBitmap = viewModel.rotatePhotoIfNeeded(requireContext(),uritobitmap)
                filterBitmap = uritobitmap
            } else {
                filterBitmap = UIApplication.tempBitmap!!
            }
            if (UIApplication.photoSaved == "saved") {
             filterBitmap = viewModel.getBitmapFromInternalStorageByPosition(requireContext(),position)
            }
            var filteredBitmap = viewModel.applyFilterOnBitmapFromPosition(requireContext(),filterBitmap,position)
            binding.imgEditor.setImageBitmap(filteredBitmap)
            //UIApplication.tempEditedPhoto = binding.imgEditor.drawToBitmap()

        }
        binding.rvFilter.adapter = filterAdapter
    }


    fun loadData() {

        if(UIApplication.editExisting == 1) {
            var position = UIApplication.currentPosition
            val rotatedBitmap = viewModel.rotatePhotoIfNeeded(requireContext(),
                                viewModel.getBitmapFromInternalStorageByPosition(requireContext(),position))
            val previewBitmap = rotatedBitmap
            filterList = viewModel.returnBitmapListWithFiltersApplied(requireContext(), previewBitmap)
            UIApplication.editExisting = 0
        } else {
//            val rotatedBitmap = viewModel.rotatePhotoIfNeeded(requireContext(),
//                                viewModel.preparePreviewBitmap(requireContext()) )
//            val previewBitmap = rotatedBitmap
            val previewBitmap = viewModel.preparePreviewBitmap(requireContext())
            filterList = viewModel.returnBitmapListWithFiltersApplied(requireContext(), previewBitmap)
        }

    }

    private fun setSeekPercentVisible(isVisible: Boolean){
        val seek = binding.seekBar
        val percent = binding.percent
        seek.isVisible = isVisible
        percent.isVisible = isVisible
    }




}