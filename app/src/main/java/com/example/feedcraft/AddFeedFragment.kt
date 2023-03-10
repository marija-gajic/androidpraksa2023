package com.example.feedcraft

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.feedcraft.databinding.FragmentAddFeedBinding
import com.example.feedcraft.databinding.FragmentDeleteFeedBinding

class AddFeedFragment : DialogFragment() {
    private var _binding: FragmentAddFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val addGallery = binding.btnGallery
        val addCamera = binding.btnCamera

        addGallery.setOnClickListener {
            //TODO
        }
        addCamera.setOnClickListener {
            //TODO
        }
    }

}