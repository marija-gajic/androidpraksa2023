package com.example.feedcraft

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.feedcraft.databinding.FragmentFinishBinding
import com.example.feedcraft.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnDone = binding.btnDoneSchedule
        val btnBack = binding.btnBackSchedule
        val addPicture = binding.addPhotoCheckbox
        val datePicker = binding.datePicker
        val timePicker = binding.timePicker

        btnDone.setOnClickListener {
            //TODO
        }
        btnBack.setOnClickListener {
            val actionBack = ScheduleFragmentDirections.actionScheduleFragmentToFinishFragment()
            findNavController().navigate(actionBack)
        }
        addPicture.setOnClickListener {
            //TODO
        }
        datePicker.setOnClickListener {
            //TODO
        }
        timePicker.setOnClickListener {
            //TODO
        }
    }

}