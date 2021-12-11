package com.example.alodokter_rakamin_android_kelompok1.view.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.data.entity.LoginEntity
import com.example.alodokter_rakamin_android_kelompok1.view.MainActivity
import com.example.alodokter_rakamin_android_kelompok1.view.viewmodel.ViewModelFactory

class LoginActivity: AppCompatActivity() {

    private lateinit var loginActivity: LoginActivity
    private lateinit var btnLogin : Button
    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText

    companion object{
        const val EMAIL = "EMAIL"
        const val SHARE = "SHARE"
        const val SESSION = "SESSION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences = getSharedPreferences(SHARE,Context.MODE_PRIVATE)
        btnLogin = findViewById(R.id.btnLogin)
        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)

        if (sharedPreferences.getBoolean(SESSION, false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            //inisialisasi viewmodel class
            val loginViewModel =
                ViewModelProvider(
                    viewModelStore,
                    ViewModelFactory()
                )[LoginViewModel::class.java]

            // button login click
            loginActivity.btnLogin.setOnClickListener {

                val email = loginActivity.edtEmail.text.toString().trim()
                val password = loginActivity.edtPassword.text.toString().trim()

                when {
                    email.isEmpty() -> {
                        loginActivity.edtEmail.error = "Email required"
                        loginActivity.edtEmail.requestFocus()
                    }
                    password.isEmpty() -> {
                        loginActivity.edtPassword.error = "Password required"
                        loginActivity.edtPassword.requestFocus()
                    }
                    else -> {

                        //kirim parameter ke viewmodel
                        loginViewModel.loginEntity = LoginEntity(
                            email = email,
                            password_digest = password
                        )

                        //observer viewmodel livedata
                        loginViewModel.getLogin().observe(this, { it ->
                            if (LoginEntity.) {
                                //simpan data ke cache untuk profil
                                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                editor.putString(EMAIL, it.email)
                                editor.putBoolean(SESSION, true)
                                editor.apply()

                                startActivity(Intent(this, MainActivity::class.java))
                            } else {
                                Toast.makeText(
                                    this,
                                    "Username atau password salah",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        })
                    }
                }
            }
        }
    }
}