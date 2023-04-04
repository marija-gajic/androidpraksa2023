package com.example.feedcraft.activitySplash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ProgressBar
import com.example.feedcraft.activityMain.MainActivity
import com.example.feedcraft.R

class SplashActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val prog = findViewById<ProgressBar>(R.id.loading_splash)
        val loading_time = resources.getInteger(R.integer.loading_timer).toLong()
        prog.max = loading_time.toInt()

            object : CountDownTimer(loading_time, 1000) {
            override fun onTick(p0: Long) {
                prog.progress = prog.progress + 1000
            }
            override fun onFinish() {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)

                finish()
            }
        }.start()




    }


}