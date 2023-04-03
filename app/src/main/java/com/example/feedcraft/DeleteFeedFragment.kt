package com.example.feedcraft

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
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

//        binding.txtDelete.typeface =
//            ResourcesCompat.getFont(requireContext(), R.font.poppins_regular)

        btnOk.setOnClickListener {
            //viewModel.deleteBitmapFromInternalStorageByPosition(requireContext(), itemPosition)
            //previews.adapter?.notifyItemRemoved(itemPosition)
            Toast.makeText(requireContext(), "Photo deleted!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
        btnCancel.setOnClickListener {
//            val actionCancel = DeleteFeedFragmentDirections.actionDeleteFeedFragmentToFeedFragment()
//            findNavController().navigate(actionCancel)
            findNavController().navigateUp()
        }
    }


}