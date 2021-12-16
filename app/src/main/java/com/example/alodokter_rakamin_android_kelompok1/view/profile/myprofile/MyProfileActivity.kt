package com.example.alodokter_rakamin_android_kelompok1.view.profile.myprofile

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse
import com.example.alodokter_rakamin_android_kelompok1.databinding.ActivityMyProfileBinding
import com.example.alodokter_rakamin_android_kelompok1.view.MainActivity
import com.example.alodokter_rakamin_android_kelompok1.view.profile.aboutus.AboutUsActivity
import com.example.alodokter_rakamin_android_kelompok1.view.profile.changepassword.ChangePasswordActivity
import com.example.alodokter_rakamin_android_kelompok1.view.profile.editprofile.EditProfileActivity
import com.google.gson.Gson

class MyProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyProfileBinding
    private lateinit var user: String
    private lateinit var json: UserResponse

    companion object{
        const val MY_PROFILE_ACTIVITY = "my_profile"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = intent.getStringExtra(MY_PROFILE_ACTIVITY) as String
        json = Gson().fromJson(user, UserResponse::class.java)
        binding.tvProfileName.text = json.user?.full_name
        binding.tvPhone.text = resources.getString(R.string.phone_format,json.user?.phone_number)
        binding.btEditProfile.setOnClickListener{
            Intent(this, EditProfileActivity::class.java).also{
                it.putExtra(EditProfileActivity.EDIT_PROFILE,json.toString())
                startActivity(it)
            }
        }
        binding.btChangePassword.setOnClickListener{
            Intent(this, ChangePasswordActivity::class.java).also{
                it.putExtra(ChangePasswordActivity.CHANGE_PASSWORD,json.toString())
                startActivity(it)
            }
        }
        binding.btAboutUs.setOnClickListener{
            Intent(this, AboutUsActivity::class.java).also{
                startActivity(it)
            }
        }
        binding.btSignOut.setOnClickListener {
            SharedPreferences(this).setLogout()
            Intent(this, MainActivity::class.java).also{
                it.putExtra(MainActivity.TO_LOGIN,"not_login")
                startActivity(it)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            user = data?.getStringExtra(MY_PROFILE_ACTIVITY) as String
            json = Gson().fromJson(user, UserResponse::class.java)
            binding.tvProfileName.text = json.user?.full_name
            binding.tvPhone.text = resources.getString(R.string.phone_format,json.user?.phone_number)
        }
    }
}