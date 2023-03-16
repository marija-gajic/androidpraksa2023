package com.example.feedcraft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.feedcraft.databinding.FragmentAddCaptionBinding

class AddCaptionFragment : DialogFragment() {
    private var _binding: FragmentAddCaptionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditorViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCaptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnOk = binding.btnCaptionOk
        val btnCancel = binding.btnCaptionCancel
        val caption = binding.txtAddCaption

        caption.setText(viewModel.edits.value?.caption)

        btnOk.setOnClickListener {
            val txt = caption.text.toString()
            viewModel.setCaption(txt)
            dismiss()
        }
        btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}