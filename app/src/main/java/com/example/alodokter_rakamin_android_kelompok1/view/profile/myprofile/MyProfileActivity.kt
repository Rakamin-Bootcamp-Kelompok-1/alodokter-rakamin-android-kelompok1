package com.example.alodokter_rakamin_android_kelompok1.view.profile.myprofile

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                user = result.data?.getStringExtra(MY_PROFILE_ACTIVITY) as String
                json = Gson().fromJson(user, UserResponse::class.java)
                binding.tvProfileName.text = json.user?.full_name
                binding.tvPhone.text = resources.getString(R.string.phone_format,json.user?.phone_number)
            }
        }

        binding.ibBack.setOnClickListener {
            finish()
        }

        binding.btEditProfile.setOnClickListener{
            startForResult.launch(Intent(this, EditProfileActivity::class.java).also{
                val jsonString = Gson().toJson(json)
                it.putExtra(EditProfileActivity.EDIT_PROFILE,jsonString)
            })
        }
        binding.btChangePassword.setOnClickListener{
            startForResult.launch(Intent(this, ChangePasswordActivity::class.java).also{
                val jsonString = Gson().toJson(json)
                it.putExtra(ChangePasswordActivity.CHANGE_PASSWORD,jsonString)
            })
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
}