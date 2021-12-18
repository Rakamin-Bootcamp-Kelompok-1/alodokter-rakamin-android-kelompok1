package com.example.alodokter_rakamin_android_kelompok1.view.profile.aboutus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.view.profile.myprofile.MyProfileActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*

class AboutUsActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

//        val actionBar = supportActionBar
//
//        actionBar!!.title = "Edit Profile"
//
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()
        ibBack.setOnClickListener {
            finish()
        }
    }
}