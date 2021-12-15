package com.example.alodokter_rakamin_android_kelompok1.view.profile.changepassword

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.view.profile.myprofile.MyProfileActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*

class ChangePasswordActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

//        val actionBar = supportActionBar
//
//        actionBar!!.title = "Edit Profile"
//
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()
        ibBack.setOnClickListener {
            Intent (this, MyProfileActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}