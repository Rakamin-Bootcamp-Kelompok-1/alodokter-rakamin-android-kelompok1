package com.example.alodokter_rakamin_android_kelompok1.view.profile.changepassword

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.data.repository.UserRepository
import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse
import com.example.alodokter_rakamin_android_kelompok1.databinding.ActivityChangePasswordBinding
import com.example.alodokter_rakamin_android_kelompok1.view.profile.myprofile.MyProfileActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_edit_profile.*

class ChangePasswordActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var viewModel: ChangePasswordViewModel
    private lateinit var json: UserResponse

    companion object{
        const val CHANGE_PASSWORD = "change_password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.getStringExtra(CHANGE_PASSWORD)
        json = Gson().fromJson(user, UserResponse::class.java)
        viewModel = ViewModelProvider(this)[ChangePasswordViewModel::class.java]
        viewModel.setRepository(UserRepository())
        viewModel.setUser(json)
        isErrorsEnable()
        afterTextChanged()
        viewModel.isButtonEnabled.observe(this) {
            binding.btSaveEdit.isEnabled = it
        }
        binding.btSaveEdit.setOnClickListener {
            viewModel.editPassword(this).observe(this){
                when(it){
                    is ApiResponse.Success -> {
                        binding.loading.hide()
                        val snackBar = Snackbar.make(parentEditProfile, resources.getString(R.string.success_change_password), Snackbar.LENGTH_LONG)
                        snackBar.setBackgroundTint(ContextCompat.getColor(this,R.color.error_red))
                        snackBar.show()
                        json = it.data
                    }
                    is ApiResponse.Error -> {
                        binding.loading.hide()
                        val snackBar = Snackbar.make(parentEditProfile, it.error, Snackbar.LENGTH_LONG)
                        snackBar.setBackgroundTint(ContextCompat.getColor(this,R.color.error_red))
                        snackBar.show()
                    }
                    is ApiResponse.Loading -> {
                        binding.loading.show()
                    }
                }
            }
        }
        supportActionBar?.hide()
        binding.ibBack.setOnClickListener {
            Intent (this, MyProfileActivity::class.java).also {
                it.putExtra(MyProfileActivity.MY_PROFILE_ACTIVITY,json.toString())
                startActivity(it)
                finish()
            }
        }
    }

    private fun isErrorsEnable() {
        viewModel.isErrorEnabledConfirmPassword.observe(this){
            binding.txtInputConfirmPassword.isErrorEnabled = it
        }

        viewModel.textErrorConfirmPassword.observe(this){
            binding.txtInputConfirmPassword.error = it
        }

        viewModel.isErrorEnabledNewPassword.observe(this){
            binding.txtNewPassword.isErrorEnabled = it
        }

        viewModel.textErrorNewPassword.observe(this){
            binding.txtNewPassword.error = it
        }

        viewModel.isErrorEnabledOldPassword.observe(this){
            binding.txtOldPassword.isErrorEnabled = it
        }

        viewModel.textErrorOldPassword.observe(this){
            binding.txtOldPassword.error = it
        }

    }

    private fun afterTextChanged(){
        binding.edtConfirmPassword.doAfterTextChanged {
            viewModel.afterTextChangeConfirmPassword(it,resources.getString(R.string.different_confirm_password))
        }
        binding.edtNewPassword.doAfterTextChanged {
            viewModel.afterTextChangeNewPassword(it,resources.getString(R.string.min_password))
        }
        binding.edtOldPassword.doAfterTextChanged {
            viewModel.afterTextChangeOldPassword(it,resources.getString(R.string.min_password))
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(MyProfileActivity.MY_PROFILE_ACTIVITY,json.toString())
        setResult(RESULT_OK,intent)
        finish()
    }
}