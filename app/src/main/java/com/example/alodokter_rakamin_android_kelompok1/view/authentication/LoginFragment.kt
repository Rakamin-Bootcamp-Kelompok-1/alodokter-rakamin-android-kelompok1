package com.example.alodokter_rakamin_android_kelompok1.view.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.example.alodokter_rakamin_android_kelompok1.data.AuthRepository
import com.example.alodokter_rakamin_android_kelompok1.databinding.FragmentLoginBinding
import com.example.alodokter_rakamin_android_kelompok1.view.reset.ResetPasswordActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment: Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private var isErrorText = ""
    private var isError = 0

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
        binding.btnLogin.setOnClickListener {
            viewModel.getLogin().observe(viewLifecycleOwner){ user ->
                Log.d("TESTA",user.toString())
                val token = user.token
                lifecycleScope.launch(Dispatchers.IO) {
                    if (token != null) {
                        SharedPreferences(requireContext()).saveAuthToken(token)
                    }
                }
                isErrorText = user.error.toString()
                user?.token?.let { Log.d("TEST", it) }
                if (token != null) {
                    user.let { SharedPreferences(requireContext()).setUser(it, true) }
                    navController.popBackStack()
                } else if (user?.error != null) {
                    Toast.makeText(requireContext(),isErrorText,Toast.LENGTH_LONG).show()
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
