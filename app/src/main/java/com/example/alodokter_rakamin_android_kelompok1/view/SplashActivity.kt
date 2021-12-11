package com.example.alodokter_rakamin_android_kelompok1.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.alodokter_rakamin_android_kelompok1.R

class SplashActivity : AppCompatActivity() {
    private val splashTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }, splashTime)
    }
}