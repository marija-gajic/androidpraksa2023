package com.example.feedcraft.activityMain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.feedcraft.R
import com.example.feedcraft.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigation = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigation.setupWithNavController(navigation)


//        navController.addOnDestinationChangedListener {  _, destination, _ ->
//        when(destination.id) {
//            R.id.editFragment -> binding.bottomNavigation.visibility = View.GONE
//
//        }}


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


