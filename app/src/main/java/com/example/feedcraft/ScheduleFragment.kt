package com.example.feedcraft

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.feedcraft.databinding.FragmentFinishBinding
import com.example.feedcraft.databinding.FragmentScheduleBinding
import java.text.SimpleDateFormat
import java.util.*

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

//            val today = Calendar.getInstance()
//            //Toast.makeText(requireContext(), "clicked", Toast.LENGTH_SHORT).show()
//            datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
//                today.get(Calendar.DAY_OF_MONTH)
//
//            ) { view, year, month, day ->
//                val month = month + 1
//                val msg = "You Selected: $day/$month/$year"
//                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
//            }
        }
        timePicker.setOnClickListener {
            //TODO
        }

    }



}