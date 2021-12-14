package com.example.alodokter_rakamin_android_kelompok1.view.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.example.alodokter_rakamin_android_kelompok1.data.repository.AuthRepository
import com.example.alodokter_rakamin_android_kelompok1.databinding.FragmentLoginBinding
import com.example.alodokter_rakamin_android_kelompok1.view.reset.ResetPasswordActivity
import com.google.android.material.snackbar.Snackbar

class LoginFragment: Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel.setRepository(AuthRepository())
        val navController = findNavController()
        binding.edtEmail.doAfterTextChanged {
            viewModel.afterTextChangeEmail(it,resources.getString(R.string.not_email_address))
        }
        binding.edtPassword.doAfterTextChanged {
            viewModel.afterTextChangePassword(it,resources.getString(R.string.min_password))
        }

        viewModel.isButtonEnabled.observe(viewLifecycleOwner) {
            binding.btnLogin.isEnabled = it
        }

        viewModel.isErrorEnabledEmail.observe(viewLifecycleOwner){
            binding.txtInputEmail.isErrorEnabled = it
        }
        viewModel.textErrorEmail.observe(viewLifecycleOwner){
            binding.txtInputEmail.error = it
        }

        viewModel.isErrorEnabledPassword.observe(viewLifecycleOwner){
            binding.txtInputPassword.isErrorEnabled = it
        }
        viewModel.textErrorPassword.observe(viewLifecycleOwner){
            binding.txtInputPassword.error = it
        }
        binding.loading.hide()
        binding.btnLogin.setOnClickListener {
            viewModel.getLogin().observe(viewLifecycleOwner){
                when(it){
                    is ApiResponse.Success -> {
                        binding.loading.hide()
                        val user = it.data
                        val token = user.token as String
                        user.let { SharedPreferences(requireContext()).setUser(token, true) }
                        navController.popBackStack()
                    }
                    is ApiResponse.Error -> {
                        binding.loading.hide()
                        val snackBar = Snackbar.make(binding.parentLoginLayout, it.error, Snackbar.LENGTH_LONG)
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
        binding.forgotPassword.setOnClickListener {
            val intent = Intent(context,ResetPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.txtNextSignUp.setOnClickListener {
            navController.navigate(R.id.registerFragment)
        }
    }
}
