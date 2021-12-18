package com.example.alodokter_rakamin_android_kelompok1.view.booking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.view.MainActivity
import kotlinx.android.synthetic.main.activity_success_booking.*

class SuccessBookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_booking)

        btnHistory.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.TO_LOGIN, "login")
            startActivity(intent)
        }

        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.TO_LOGIN, "login")
            startActivity(intent)
        }
    }
}