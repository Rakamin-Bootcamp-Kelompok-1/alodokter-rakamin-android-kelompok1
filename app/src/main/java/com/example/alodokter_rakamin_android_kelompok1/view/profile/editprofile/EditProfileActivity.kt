package com.example.alodokter_rakamin_android_kelompok1.view.profile.editprofile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.data.repository.UserRepository
import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse
import com.example.alodokter_rakamin_android_kelompok1.view.profile.myprofile.MyProfileActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity()  {

    private lateinit var viewModel: EditProfileViewModel

    companion object{
        const val EDIT_PROFILE = "edit_profile"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        val user = intent.getStringExtra(EDIT_PROFILE)
        val json = Gson().fromJson(user,UserResponse::class.java)
        viewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]
        initText(json)
        viewModel.setRepository(UserRepository())
        viewModel.setUser(json)
        isErrorsEnable()
        afterTextChanged()
        viewModel.isButtonEnabled.observe(this) {
            btSaveEdit.isEnabled = it
        }
        btSaveEdit.setOnClickListener {
            viewModel.editProfile(this).observe(this){

            }
        }
        supportActionBar?.hide()
        ibBack.setOnClickListener {
            Intent (this, MyProfileActivity::class.java).also {
                //add data user to intent
                startActivity(it)
                finish()
            }
        }
    }

    private fun initText(json: UserResponse){
        tvFormFullName.setText(json.user?.full_name)
        tvFormEmail.setText(json.user?.email)
        tvFormBirthDate.setText(json.user?.birth_date)
        tvFormPhoneNumber.setText(json.user?.phone_number)
    }

    private fun isErrorsEnable() {
        viewModel.isErrorEnabledEmail.observe(this){
//            binding.tvFormEmail.isErrorEnabled = it
        }

        viewModel.textErrorEmail.observe(this){
//            binding.txtInputEmail.error = it
        }

        viewModel.isErrorEnabledFullName.observe(this){
//            binding.txtInputName.isErrorEnabled = it
        }

        viewModel.textErrorFullName.observe(this){
//            binding.txtInputName.error = it
        }

        viewModel.isErrorEnabledDate.observe(this){
//            binding.txtInputDateBirth.isErrorEnabled = it
        }

        viewModel.textErrorDate.observe(this){
//            binding.txtInputDateBirth.error = it
        }

        viewModel.isErrorEnabledPhoneNumber.observe(this){
//            binding.txtInputNumber.isErrorEnabled = it
        }

        viewModel.textErrorPhoneNumber.observe(this){
//            binding.txtInputNumber.error = it
        }
    }

    private fun afterTextChanged(){
//        binding.edtFullName.doAfterTextChanged {
//            viewModel.afterTextChangeFullName(it,resources.getString(R.string.min_full_name))
//        }
//        binding.edtEmail.doAfterTextChanged {
//            viewModel.afterTextChangeEmail(it,resources.getString(R.string.not_email_address))
//        }
//        binding.edtBirthDate.doAfterTextChanged {
//            viewModel.afterTextChangeDate(it,resources.getString(R.string.min_date))
//        }
//        binding.edtPhoneNumber.doAfterTextChanged {
//            viewModel.afterTextChangePhoneNumber(it,resources.getString(R.string.min_phone_number))
//        }
//        binding.edtGender.doAfterTextChanged {
//            viewModel.afterTextChangeGender(it,resources.getString(R.string.min_gender))
//        }
//        binding.edtPassword.doAfterTextChanged {
//            viewModel.afterTextChangePassword(it,resources.getString(R.string.min_password))
//        }
//        binding.edtConfirmPassword.doAfterTextChanged {
//            viewModel.afterTextChangeConfirmPassword(it,resources.getString(R.string.different_confirm_password))
//        }
    }

}