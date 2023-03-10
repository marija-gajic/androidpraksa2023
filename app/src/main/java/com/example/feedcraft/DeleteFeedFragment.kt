package com.example.feedcraft

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.feedcraft.databinding.FragmentAddCaptionBinding
import com.example.feedcraft.databinding.FragmentDeleteFeedBinding

class DeleteFeedFragment : DialogFragment() {
    private var _binding: FragmentDeleteFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeleteFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnOk = binding.btnDeleteOk
        val btnCancel = binding.btnDeleteCancel

        btnOk.setOnClickListener {
            //TODO
        }
        btnCancel.setOnClickListener {
            val actionCancel = DeleteFeedFragmentDirections.actionDeleteFeedFragmentToFeedFragment()
            findNavController().navigate(actionCancel)
        }
    }


}