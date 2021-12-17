package com.example.alodokter_rakamin_android_kelompok1.view.profile.editprofile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.data.repository.UserRepository
import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse
import com.example.alodokter_rakamin_android_kelompok1.databinding.ActivityEditProfileBinding
import com.example.alodokter_rakamin_android_kelompok1.view.profile.myprofile.MyProfileActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.util.*

class EditProfileActivity : AppCompatActivity()  {

    private lateinit var viewModel: EditProfileViewModel
    private lateinit var binding : ActivityEditProfileBinding
    private lateinit var json: UserResponse

    companion object{
        const val EDIT_PROFILE = "edit_profile"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("1425f","TESTA")
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.getStringExtra(EDIT_PROFILE)
        json = Gson().fromJson(user,UserResponse::class.java)
        viewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]
        initText(json)
        viewModel.setRepository(UserRepository())
        viewModel.setUser(json)
        afterTextChanged()
        isErrorsEnable()
        datePicker()
        viewModel.isButtonEnabled.observe(this) {
            binding.btSaveEdit.isEnabled = it
        }
        binding.loading.hide()
        binding.btSaveEdit.setOnClickListener {
            viewModel.editProfile(this).observe(this){
                when(it){
                    is ApiResponse.Success -> {
                        binding.loading.hide()
                        binding.edtFullName.setText(it.data.user?.full_name)
                        binding.edtEmail.setText(it.data.user?.email)
                        binding.edtBirthDate.setText(it.data.user?.birth_date)
                        binding.edtPhoneNumber.setText(it.data.user?.phone_number)
                        json = it.data
                    }
                    is ApiResponse.Error -> {
                        binding.loading.hide()
                        val snackBar = Snackbar.make(binding.parentEditProfile, it.error, Snackbar.LENGTH_LONG)
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
                val jsonString = Gson().toJson(json)
                it.putExtra(MyProfileActivity.MY_PROFILE_ACTIVITY,jsonString)
                startActivity(it)
                finish()
            }
        }
    }

    private fun initText(json: UserResponse){
        binding.edtFullName.setText(json.user?.full_name)
        binding.edtEmail.setText(json.user?.email)
        binding.edtBirthDate.setText(json.user?.birth_date)
        binding.edtPhoneNumber.setText(json.user?.phone_number)
    }

    private fun isErrorsEnable() {
        viewModel.isErrorEnabledEmail.observe(this){
            binding.txtInputEmail.isErrorEnabled = it
        }

        viewModel.textErrorEmail.observe(this){
            binding.txtInputEmail.error = it
        }

        viewModel.isErrorEnabledFullName.observe(this){
            binding.txtInputName.isErrorEnabled = it
        }

        viewModel.textErrorFullName.observe(this){
            binding.txtInputName.error = it
        }

        viewModel.isErrorEnabledDate.observe(this){
            binding.txtInputDateBirth.isErrorEnabled = it
        }

        viewModel.textErrorDate.observe(this){
            binding.txtInputDateBirth.error = it
        }

        viewModel.isErrorEnabledPhoneNumber.observe(this){
            binding.txtInputNumber.isErrorEnabled = it
        }

        viewModel.textErrorPhoneNumber.observe(this){
            binding.txtInputNumber.error = it
        }
    }

    private fun afterTextChanged(){
        binding.edtFullName.doAfterTextChanged {
            viewModel.afterTextChangeFullName(it,resources.getString(R.string.min_full_name))
        }
        binding.edtEmail.doAfterTextChanged {
            viewModel.afterTextChangeEmail(it,resources.getString(R.string.not_email_address))
        }
        binding.edtBirthDate.doAfterTextChanged {
            viewModel.afterTextChangeDate(it,resources.getString(R.string.min_date))
        }
        binding.edtPhoneNumber.doAfterTextChanged {
            viewModel.afterTextChangePhoneNumber(it,resources.getString(R.string.min_phone_number))
        }
    }

    private fun datePicker(){
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(resources.getString(R.string.date_of_birth))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        binding.edtBirthDate.setOnClickListener {
            datePicker.show(supportFragmentManager,"DATE_TAG")
        }
        datePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            binding.edtBirthDate.setText(resources.getString(R.string.date_format,day,month,year))
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        val jsonString = Gson().toJson(json)
        intent.putExtra(MyProfileActivity.MY_PROFILE_ACTIVITY,jsonString)
        setResult(RESULT_OK,intent)
        Log.d("1425f","TEST")
//        Intent (this, MyProfileActivity::class.java).also {
//            val jsonString = Gson().toJson(json)
//            it.putExtra(MyProfileActivity.MY_PROFILE_ACTIVITY,jsonString)
//            startActivity(it)
//            finish()
//        }
        finish()
    }

}