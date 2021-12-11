package com.example.alodokter_rakamin_android_kelompok1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

//        val actionbar = supportActionBar
//        actionbar!!.title = "Edit Profile"
//
//        actionbar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()
        ibBack.setOnClickListener {
            Intent (this, MyProfileActivity::class.java).also {
                startActivity(it)
            }
        }
//        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
//        supportActionBar?.setDisplayShowCustomEnabled(true)
//        supportActionBar?.setCustomView(R.layout.toolbar)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }
}