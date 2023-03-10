package com.example.feedcraft

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.feedcraft.databinding.FragmentEditBinding
import com.example.feedcraft.databinding.FragmentScheduleBinding
import com.example.feedcraft.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnRate = binding.imgRateArrow
        val btnRecommend = binding.imgRecommendArrow
        val btnFeedback = binding.imgFeedbackArrow
        val btnMoreApps = binding.imgMoreAppsArrow
        val btnPrivacy = binding.imgPrivacyArrow
        val darkModeOn = binding.darkModeOn
        val darkModeOff = binding.darkModeOff

        btnRate.setOnClickListener {
            //TODO
        }
        btnRecommend.setOnClickListener {
            //TODO
        }
        btnFeedback.setOnClickListener {
            //TODO
        }
        btnMoreApps.setOnClickListener {
            //TODO
        }
        btnPrivacy.setOnClickListener {
            //TODO
        }
        darkModeOn.setOnClickListener {
            //TODO
        }
        darkModeOff.setOnClickListener {
            //TODO
        }
    }
}