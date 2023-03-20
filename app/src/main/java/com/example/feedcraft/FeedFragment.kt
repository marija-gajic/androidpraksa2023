package com.example.feedcraft

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.feedcraft.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditorViewModel by activityViewModels()
    private var previewList : MutableList<PhotoPreviewModel> = mutableListOf()
    private lateinit var previewAdapter: PhotoPreviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

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
                //Log.d("mylog", "kliknut item na poziciji: $it")

                handleOptionsOnItemClick(checked)

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
            startActivityForResult(Intent(requireContext(), EditorActivity::class.java), 1005)
        }
        colorCode.setOnClickListener {
            //TODO
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
        //val color = ContextCompat.getColor(requireContext(), R.color.buttons)
        for (preview in listing) {
            val dominantColor = preview.getPixel(0,0)
            previewList.add(PhotoPreviewModel(preview, dominantColor))
        }
    }

    override fun onResume() {
        super.onResume()
        if (UIApplication.photoSaved == "saved") {
            UIApplication.photoSaved = ""

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