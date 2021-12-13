package com.example.alodokter_rakamin_android_kelompok1.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.example.alodokter_rakamin_android_kelompok1.view.onboard.OnBoardingActivity

class SplashActivity : AppCompatActivity() {
    private val splashTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        Handler(mainLooper).postDelayed({
            if(SharedPreferences(this).isFirstTimeLaunch()){
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(MainActivity.TO_LOGIN,"not_login")
                startActivity(intent)
                finish()
            }
        }, splashTime)
    }
}