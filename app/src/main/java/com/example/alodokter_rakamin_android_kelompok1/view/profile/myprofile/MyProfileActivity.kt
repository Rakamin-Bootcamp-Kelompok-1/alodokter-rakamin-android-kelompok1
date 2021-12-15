package com.example.alodokter_rakamin_android_kelompok1.view.profile.myprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.view.profile.aboutus.AboutUsActivity
import com.example.alodokter_rakamin_android_kelompok1.view.profile.changepassword.ChangePasswordActivity
import com.example.alodokter_rakamin_android_kelompok1.view.profile.editprofile.EditProfileActivity
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        btEditProfile.setOnClickListener{
            Intent(this, EditProfileActivity::class.java).also{
                startActivity(it)
            }
        }
        btChangePassword.setOnClickListener{
            Intent(this, ChangePasswordActivity::class.java).also{
                startActivity(it)
            }
        }
        btAboutUs.setOnClickListener{
            Intent(this, AboutUsActivity::class.java).also{
                startActivity(it)
            }
        }
    }
}