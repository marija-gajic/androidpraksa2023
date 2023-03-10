package com.example.feedcraft

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.feedcraft.databinding.ActivityMainBinding
import com.example.feedcraft.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!


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

        fabAdd.setOnClickListener{
            val actionAdd = FeedFragmentDirections.actionFeedFragmentToAddFeedFragment()
            findNavController().navigate(actionAdd)
        }
        deleteFeed.setOnClickListener {
            val actionDelete = FeedFragmentDirections.actionFeedFragmentToDeleteFeedFragment()
            findNavController().navigate(actionDelete)
        }
        editPhoto.setOnClickListener {
            val actionEdit = FeedFragmentDirections.actionFeedFragmentToEditFragment()
            findNavController().navigate(actionEdit)
        }
        colorCode.setOnClickListener {
            //TODO
        }
        btnBack.setOnClickListener {
            val actionBack = FeedFragmentDirections.actionFeedFragmentToHomeFragment()
            findNavController().navigate(actionBack)
        }

    }

}