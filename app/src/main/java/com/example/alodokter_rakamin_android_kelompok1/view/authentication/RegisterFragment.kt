package com.example.alodokter_rakamin_android_kelompok1.view.authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.example.alodokter_rakamin_android_kelompok1.data.AuthRepository
import com.example.alodokter_rakamin_android_kelompok1.databinding.FragmentRegisterBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class RegisterFragment: Fragment() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding
    private var isErrorText = ""

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
        binding.btnRegister.setOnClickListener {
            viewModel.getRegister().observe(viewLifecycleOwner){ user ->
                val token = user.token
//                lifecycleScope.launch(Dispatchers.IO) {
//                    if (token != null) {
//                        SharedPreferences(requireContext()).saveAuthToken(token)
//                    }
//                }
                isErrorText = user.error.toString()
                user?.token?.let { Log.d("TEST", it) }
                if (token != null) {
                    user.let { SharedPreferences(requireContext()).setUser(it, true) }
                    navController.navigate(R.id.navigation_home)
                } else if (user?.error != null) {
                    Toast.makeText(requireContext(),isErrorText, Toast.LENGTH_LONG).show()
                }
            }

            Log.d("TEST","TEST")
        }
        binding.txtNextSignIn.setOnClickListener {
            navController.popBackStack()
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