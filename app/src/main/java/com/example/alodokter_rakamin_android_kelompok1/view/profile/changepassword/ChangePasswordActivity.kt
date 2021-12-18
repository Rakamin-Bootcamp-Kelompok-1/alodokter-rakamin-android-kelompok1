package com.example.alodokter_rakamin_android_kelompok1.view.profile.changepassword

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
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
        afterTextChanged()
        isErrorsEnable()
        binding.loading.hide()
        viewModel.isButtonEnabled.observe(this) {
            binding.btSaveEdit.isEnabled = it
        }
        binding.btSaveEdit.setOnClickListener {
            getData()
        }
        supportActionBar?.hide()
        binding.ibBack.setOnClickListener {
            Intent (this, MyProfileActivity::class.java).also {
                val jsonString = Gson().toJson(json)
                it.putExtra(MyProfileActivity.MY_PROFILE_ACTIVITY,jsonString)
                startActivity(it)
                finish()
            }
        }
        binding.edtConfirmPassword.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when(actionId){
                EditorInfo.IME_ACTION_DONE -> {
                    getData()
                    true
                }
                else -> false
            }
        }
    }

    private fun getData(){
        if(viewModel.checkPassword()){
            viewModel.editPassword(this).observe(this){
                when(it){
                    is ApiResponse.Success -> {
                        binding.loading.hide()
                        val snackBar = Snackbar.make(binding.parent, resources.getString(R.string.success_change_password), Snackbar.LENGTH_LONG)
                        snackBar.setBackgroundTint(ContextCompat.getColor(this,R.color.main_blue))
                        snackBar.show()
                        json = it.data
                        binding.edtNewPassword.setText("")
                        binding.edtOldPassword.setText("")
                        binding.edtConfirmPassword.setText("")
                    }
                    is ApiResponse.Error -> {
                        binding.loading.hide()
                        val snackBar = Snackbar.make(binding.parent, it.error, Snackbar.LENGTH_LONG)
                        snackBar.setBackgroundTint(ContextCompat.getColor(this,R.color.error_red))
                        snackBar.show()
                    }
                    is ApiResponse.Loading -> {
                        binding.loading.show()
                    }
                }
            }
        }
        else {
            binding.loading.hide()
            val snackBar = Snackbar.make(binding.parent, resources.getString(R.string.different_old_password), Snackbar.LENGTH_LONG)
            snackBar.setBackgroundTint(ContextCompat.getColor(this,R.color.error_red))
            snackBar.show()
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
        val jsonString = Gson().toJson(json)
        intent.putExtra(MyProfileActivity.MY_PROFILE_ACTIVITY,jsonString)
        setResult(RESULT_OK,intent)
        finish()
    }
}