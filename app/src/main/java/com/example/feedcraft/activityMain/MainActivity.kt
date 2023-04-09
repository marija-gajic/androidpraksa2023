package com.example.feedcraft.activityMain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.feedcraft.R
import com.example.feedcraft.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigation = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigation.setupWithNavController(navigation)

        }

    }


