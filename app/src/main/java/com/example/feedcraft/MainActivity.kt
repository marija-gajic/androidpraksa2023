package com.example.feedcraft

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.feedcraft.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigation = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigation.setupWithNavController(navigation)





//        NavigationBarView.OnItemSelectedListener { item ->
//            when(item.itemId) {
//                R.id.btn_home -> {
//                    // Respond to navigation item 1 click
//                    true
//                }
//                R.id.btn_feed -> {
//                    val intent = Intent(this@MainActivity, EditorActivity::class.java)
//                    startActivity(intent)
//                    true
//                }
//                R.id.btn_settings -> {
//                    // Respond to navigation item 2 click
//                    true
//                }
//                else -> false
//            }

        }
    }


