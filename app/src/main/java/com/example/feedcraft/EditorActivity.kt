package com.example.feedcraft

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.feedcraft.databinding.ActivityEditorBinding
import com.example.feedcraft.databinding.ActivityMainBinding

class EditorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val navigation = findNavController(R.id.nav_editor)


    }

}