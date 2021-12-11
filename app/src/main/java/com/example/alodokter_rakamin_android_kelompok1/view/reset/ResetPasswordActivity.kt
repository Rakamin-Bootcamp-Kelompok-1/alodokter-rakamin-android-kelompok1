package com.example.alodokter_rakamin_android_kelompok1.view.reset

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.view.LoginActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var textInput: TextInputEditText
    private lateinit var btnSubmit: MaterialButton
    private lateinit var textLayout: TextInputLayout
    private lateinit var toolbar : Toolbar
    private lateinit var back : ImageButton
    private lateinit var viewModel: ResetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        toolbar = findViewById(R.id.toolbar)
        back = findViewById(R.id.back)
        btnSubmit = findViewById(R.id.btn_submit)
        textInput = findViewById(R.id.text_input)
        textLayout = findViewById(R.id.text_input_field)
        viewModel = ViewModelProvider(this)[ResetPasswordViewModel::class.java]
        setToolbar()
        textInput.doAfterTextChanged {
            viewModel.afterChangePassword(it,resources.getString(R.string.not_email_address))
        }
        viewModel.isButtonEnabled.observe(this) {
            btnSubmit.isEnabled = it
        }
        viewModel.isHelperEnabled.observe(this){
            textLayout.isHelperTextEnabled = it
        }
        viewModel.isErrorEnabled.observe(this){
            textLayout.isErrorEnabled = it
        }
        viewModel.textError.observe(this){
            textLayout.error = it
        }
        viewModel.countdownTimer.observe(this){
            if(it > 0){
                val time = it / 1000
                textLayout.helperText = resources.getString(R.string.resend_email_timer,time)
            } else {
                textLayout.helperText = null
            }
        }
        viewModel.isResend.observe(this){
            if(it){
                val color = ContextCompat.getColor(applicationContext, R.color.black)
                val colorStateList = ColorStateList.valueOf(color)
                textLayout.setHelperTextColor(colorStateList)
            }
        }
        btnSubmit.setOnClickListener {
            viewModel.countDown().start()
            viewModel.sendEmail()
        }
        val btnSignUp = findViewById<MaterialButton>(R.id.btn_sign_up)
        btnSignUp.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setToolbar(){
        supportActionBar?.hide()
        back.setOnClickListener {
            viewModel.countDown().cancel()
            finish()
        }
    }
}