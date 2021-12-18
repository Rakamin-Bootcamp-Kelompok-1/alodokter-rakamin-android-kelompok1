package com.example.alodokter_rakamin_android_kelompok1.view.reset

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.data.repository.UserRepository
import com.example.alodokter_rakamin_android_kelompok1.view.MainActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var textInput: TextInputEditText
    private lateinit var btnSubmit: MaterialButton
    private lateinit var textLayout: TextInputLayout
    private lateinit var toolbar : Toolbar
    private lateinit var back : ImageButton
    private lateinit var viewModel: ResetPasswordViewModel
    private lateinit var loading: CircularProgressIndicator
    private lateinit var layout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        toolbar = findViewById(R.id.toolbar)
        back = findViewById(R.id.back)
        btnSubmit = findViewById(R.id.btn_submit)
        textInput = findViewById(R.id.text_input)
        textLayout = findViewById(R.id.text_input_field)
        loading = findViewById(R.id.loading)
        layout = findViewById(R.id.parentResetPasswordLayout)
        viewModel = ViewModelProvider(this)[ResetPasswordViewModel::class.java]
        viewModel.setRepository(UserRepository())
        setToolbar()
        textInput.doAfterTextChanged {
            viewModel.afterTextChange(it,resources.getString(R.string.not_email_address))
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
            getData()
        }
        loading.hide()
        val btnSignUp = findViewById<MaterialButton>(R.id.btn_sign_up)
        btnSignUp.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra(MainActivity.TO_LOGIN,"login")
            startActivity(intent)
        }
        textInput.setOnEditorActionListener { v, actionId, event ->
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
        viewModel.countDown().start()
        viewModel.sendEmail().observe(this){
            when(it){
                is ApiResponse.Success -> {
                    loading.hide()
                    val jsonObject = it.data
                    val status = jsonObject.getString("status")
                    val snackBar = Snackbar.make(layout, status, Snackbar.LENGTH_LONG)
                    snackBar.setBackgroundTint(ContextCompat.getColor(this,R.color.main_blue))
                    snackBar.show()
                }
                is ApiResponse.Error -> {
                    loading.hide()
                    val snackBar = Snackbar.make(layout, it.error, Snackbar.LENGTH_LONG)
                    snackBar.setBackgroundTint(ContextCompat.getColor(this,R.color.error_red))
                    snackBar.show()
                }
                is ApiResponse.Loading -> {
                    loading.show()
                }
            }
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