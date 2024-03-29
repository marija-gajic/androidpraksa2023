package com.example.feedcraft.activityMain

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.feedcraft.activityEditor.EditorActivity
import com.example.feedcraft.activityMain.FeedFragmentDirections
import com.example.feedcraft.data.UIApplication
import com.example.feedcraft.databinding.FragmentFeedBinding
import com.example.feedcraft.recyclerViewAdapters.PhotoPreviewAdapter
import com.example.feedcraft.recyclerViewModels.PhotoPreviewModel
import com.example.feedcraft.viewModels.EditorViewModel
import com.example.feedcraft.viewModels.MainViewModel


class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditorViewModel by activityViewModels()
    private var previewList : MutableList<PhotoPreviewModel> = mutableListOf()
    private lateinit var previewAdapter: PhotoPreviewAdapter
    var itemBitmap: Bitmap? = UIApplication.tempBitmap
    var itemBorderColor: Int = 0
    var itemDominantColor: Int = 0
    var itemPosition: Int = -1
    lateinit var itemSelected : PhotoPreviewModel
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        UIApplication.photoDeleted.observe(viewLifecycleOwner) {
            onPhotoDelete(it)
        }
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val fabAdd = binding.fabFeed
        val deleteFeed = binding.btnDeleteFeed
        val colorCode = binding.btnColorCodeFeed
        val editPhoto = binding.btnEditFeed
        val btnBack = binding.btnBackFeed
        val previews = binding.rvPreviews
        val emptyFeedImg = binding.imgEmptyFeed
        val emptyFeedTxt = binding.txtEmptyFeed


        previews.setLayoutManager(GridLayoutManager(requireContext(), 3))
        val list = viewModel.getListOfPreviewsFromStorage(requireContext())

        if (list.size == 0) {
            emptyFeedImg.isVisible = true
            emptyFeedTxt.isVisible = true
        } else {
            emptyFeedImg.isVisible = false
            emptyFeedTxt.isVisible = false
            loadPreviewPhotos()
            previewAdapter = PhotoPreviewAdapter(previewList) { position, checked ->
                handleOptionsOnItemClick(checked)
                itemPosition = position
                UIApplication.itemSelectedPosition = position
                itemBitmap = previewList[position].previewBitmap
                itemDominantColor = itemBitmap!!.getPixel(20,20)
                itemBorderColor = previewList[position].borderColor
                itemSelected = previewList[position]

                viewModel.getBitmapFromInternalStorageByPosition(requireContext(), position)

            }
            binding.rvPreviews.adapter = previewAdapter
            }


        fabAdd.setOnClickListener {
            val actionAdd = FeedFragmentDirections.actionFeedFragmentToAddFeedFragment()
            findNavController().navigate(actionAdd)
        }
        deleteFeed.setOnClickListener {
            val actionDelete = FeedFragmentDirections.actionFeedFragmentToDeleteFeedFragment()
            findNavController().navigate(actionDelete)
        }
        editPhoto.setOnClickListener {
            UIApplication.editExisting = 1
            UIApplication.currentPosition = itemPosition
            startActivity(Intent(requireContext(), EditorActivity::class.java))
        }
        colorCode.setOnClickListener {
            if (itemBorderColor == Color.argb(0, 0, 0, 0)) {
                itemSelected.borderColor = itemDominantColor
                itemBorderColor = itemDominantColor
            } else {
                itemSelected.borderColor = Color.argb(0, 0, 0, 0)
                itemBorderColor = Color.argb(0, 0, 0, 0)

            }
            previews.adapter?.notifyDataSetChanged()

        }
        btnBack.setOnClickListener {
            val actionBack = FeedFragmentDirections.actionFeedFragmentToHomeFragment()
            findNavController().navigate(actionBack)
        }
    }

    fun loadPreviewPhotos() {

        if(previewList.isNotEmpty())
            previewList.clear()

        val listing: MutableList<Bitmap> = viewModel.getListOfPreviewsFromStorage(requireContext())
        for (preview in listing) {
            val dominantColor = preview.getPixel(20,20)
            previewList.add(PhotoPreviewModel(preview, dominantColor))
        }
    }

    override fun onResume() {
        super.onResume()
        if (UIApplication.photoSaved == "saved") {
            UIApplication.photoSaved = ""

            if(previewList.isNotEmpty())
                previewAdapter.refreshFilterAdapterList(previewList)

            loadPreviewPhotos()
        }

    }

    private fun onPhotoDelete(photoDeleted: String) {
        if (photoDeleted == "deleted") {
            UIApplication.photoDeleted.postValue("")
            loadPreviewPhotos()
            previewAdapter.refreshFilterAdapterList(previewList)
        }
    }
    fun handleOptionsOnItemClick(optionsEnabled: Boolean)
    {

        if(optionsEnabled) {
            binding.fabFeed.visibility = View.GONE
            binding.btnDeleteFeed.visibility = View.VISIBLE
            binding.btnColorCodeFeed.visibility = View.VISIBLE
            binding.btnEditFeed.visibility = View.VISIBLE
        }
        else
        {
            binding.fabFeed.visibility = View.VISIBLE
            binding.btnDeleteFeed.visibility = View.GONE
            binding.btnColorCodeFeed.visibility = View.GONE
            binding.btnEditFeed.visibility = View.GONE
        }
    }



}

