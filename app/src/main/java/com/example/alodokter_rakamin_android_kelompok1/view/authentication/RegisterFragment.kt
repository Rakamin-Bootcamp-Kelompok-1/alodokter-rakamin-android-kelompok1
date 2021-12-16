package com.example.alodokter_rakamin_android_kelompok1.view.authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.example.alodokter_rakamin_android_kelompok1.data.repository.AuthRepository
import com.example.alodokter_rakamin_android_kelompok1.databinding.FragmentRegisterBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.util.*

class RegisterFragment: Fragment() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        viewModel.setRepository(AuthRepository())
        val navController = findNavController()
        val items = resources.getStringArray(R.array.gender)
        val adapter = ArrayAdapter(requireContext(),R.layout.item_gender_register,items)
        binding.edtGender.setAdapter(adapter)
        afterTextChanged()
        isErrorsEnable()
        datePicker()
        viewModel.isButtonEnabled.observe(viewLifecycleOwner) {
            binding.btnRegister.isEnabled = it
        }
        binding.loading.hide()
        binding.btnRegister.setOnClickListener {
            viewModel.getRegister().observe(viewLifecycleOwner){
                when(it){
                    is ApiResponse.Success -> {
                        binding.loading.hide()
                        val user = it.data
                        val token = user.token as String
                        user.let { SharedPreferences(requireContext()).setUser(token, true) }
                        navController.navigate(R.id.navigation_home)
                    }
                    is ApiResponse.Error -> {
                        binding.loading.hide()
                        val snackBar = Snackbar.make(binding.parentRegisterLayout, it.error, Snackbar.LENGTH_LONG)
                        snackBar.setBackgroundTint(ContextCompat.getColor(requireContext(),R.color.error_red))
                        snackBar.show()
                    }
                    is ApiResponse.Loading -> {
                        binding.loading.show()
                    }
                }
            }

            Log.d("TEST","TEST")
        }
        binding.txtNextSignIn.setOnClickListener {
            navController.navigate(R.id.loginFragment)
        }
    }

    private fun datePicker(){
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(resources.getString(R.string.date_of_birth))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        binding.edtBirthDate.setOnClickListener {
            datePicker.show(childFragmentManager,"DATE_TAG")
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

    private fun isErrorsEnable() {
        viewModel.isErrorEnabledEmail.observe(viewLifecycleOwner){
            binding.txtInputEmail.isErrorEnabled = it
        }

        viewModel.textErrorEmail.observe(viewLifecycleOwner){
            binding.txtInputEmail.error = it
        }

        viewModel.isErrorEnabledPassword.observe(viewLifecycleOwner){
            binding.txtPassword.isErrorEnabled = it
        }

        viewModel.textErrorPassword.observe(viewLifecycleOwner){
            binding.txtPassword.error = it
        }

        viewModel.isErrorEnabledFullName.observe(viewLifecycleOwner){
            binding.txtInputName.isErrorEnabled = it
        }

        viewModel.textErrorFullName.observe(viewLifecycleOwner){
            binding.txtInputName.error = it
        }

        viewModel.isErrorEnabledDate.observe(viewLifecycleOwner){
            binding.txtInputDateBirth.isErrorEnabled = it
        }

        viewModel.textErrorDate.observe(viewLifecycleOwner){
            binding.txtInputDateBirth.error = it
        }

        viewModel.isErrorEnabledPhoneNumber.observe(viewLifecycleOwner){
            binding.txtInputNumber.isErrorEnabled = it
        }

        viewModel.textErrorPhoneNumber.observe(viewLifecycleOwner){
            binding.txtInputNumber.error = it
        }

        viewModel.isErrorEnabledGender.observe(viewLifecycleOwner){
            binding.txtInputGender.isErrorEnabled = it
        }

        viewModel.textErrorGender.observe(viewLifecycleOwner){
            binding.txtInputGender.error = it
        }

        viewModel.isErrorEnabledConfirmPassword.observe(viewLifecycleOwner){
            binding.txtInputConfirmPassword.isErrorEnabled = it
        }

        viewModel.textErrorConfirmPassword.observe(viewLifecycleOwner){
            binding.txtInputConfirmPassword.error = it
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
        binding.edtGender.doAfterTextChanged {
            viewModel.afterTextChangeGender(it,resources.getString(R.string.min_gender))
        }
        binding.edtPassword.doAfterTextChanged {
            viewModel.afterTextChangePassword(it,resources.getString(R.string.min_password))
        }
        binding.edtConfirmPassword.doAfterTextChanged {
            viewModel.afterTextChangeConfirmPassword(it,resources.getString(R.string.different_confirm_password))
        }
    }
}