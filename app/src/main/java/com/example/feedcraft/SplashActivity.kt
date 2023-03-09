package com.example.feedcraft

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ProgressBar

class SplashActivity : AppCompatActivity() {
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
            }
        }.start()



    }


}