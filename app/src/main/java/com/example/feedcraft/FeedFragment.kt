package com.example.feedcraft

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.feedcraft.databinding.ActivityMainBinding
import com.example.feedcraft.databinding.FragmentFeedBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
        fabAdd.setOnClickListener{
            val actionAdd = FeedFragmentDirections.actionFeedFragmentToAddFeedFragment()
            findNavController().navigate(actionAdd)
        }
        deleteFeed.setOnClickListener {
            val actionDelete = FeedFragmentDirections.actionFeedFragmentToDeleteFeedFragment()
            findNavController().navigate(actionDelete)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FeedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}