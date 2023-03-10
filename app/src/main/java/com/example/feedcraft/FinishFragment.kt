package com.example.feedcraft

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.feedcraft.databinding.FragmentEditBinding
import com.example.feedcraft.databinding.FragmentFinishBinding


class FinishFragment : Fragment() {
    private var _binding: FragmentFinishBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnShare = binding.btnShareFinish
        val btnSchedule = binding.btnScheduleFinish
        val btnSave = binding.btnSaveFinish
        val btnDiscard = binding.btnDiscardFinish

        btnSchedule.setOnClickListener {
            val actionSchedule = FinishFragmentDirections.actionFinishFragmentToScheduleFragment()
            findNavController().navigate(actionSchedule)
        }
        btnSave.setOnClickListener {
            //TODO
        }
        btnShare.setOnClickListener {
            //TODO
        }
        btnDiscard.setOnClickListener {
            val actionDiscard = FinishFragmentDirections.actionFinishFragmentToEditFragment()
            findNavController().navigate(actionDiscard)
        }
    }

}