package com.example.alodokter_rakamin_android_kelompok1.view

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.example.alodokter_rakamin_android_kelompok1.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var textInput: TextInputEditText
    private lateinit var btnSubmit: MaterialButton
    private lateinit var textLayout: TextInputLayout
    private lateinit var toolbar : Toolbar
    private lateinit var back : ImageButton
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
//        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        toolbar = findViewById(R.id.toolbar)
        back = findViewById(R.id.back)
        btnSubmit = findViewById(R.id.btn_submit)
        textInput = findViewById(R.id.text_input)
        textLayout = findViewById(R.id.text_input_field)
        setToolbar()
        textInput.addTextChangedListener(textWatcher())
        btnSubmit.setOnClickListener {
            countDown().start()
            //auth nanti jika firebase
            // auth.sendPasswordResetEmail(email).addOnCompleteListener
        }
        val btnSignUp = findViewById<MaterialButton>(R.id.btn_sign_up)
        btnSignUp.setOnClickListener {
//            val intent = Intent(applicationContext,::class.java)
//            startActivity(intent)
        }
    }

    private fun setToolbar(){
        supportActionBar?.hide()
        back.setOnClickListener {
            countDown().cancel()
            finish()
        }
    }

    private fun textWatcher() : TextWatcher{
        val textWatcher = object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()){
                    btnSubmit.isEnabled = true
                    textLayout.error = null
                    textLayout.isErrorEnabled = false
                    email = s.toString().trim()
                } else {
                    btnSubmit.isEnabled = false
                    textLayout.error = resources.getString(R.string.not_email_address)
                    textLayout.isErrorEnabled = true
                    email = ""
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        return textWatcher
    }

    private fun countDown() : CountDownTimer {
        val color = ContextCompat.getColor(applicationContext, R.color.black)
        val colorStateList = ColorStateList.valueOf(color)
        val countDownTimer = object : CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                val time = millisUntilFinished.toInt() / 1000
                textLayout.helperText = resources.getString(R.string.resend_email_timer,time)
                textLayout.isHelperTextEnabled = true
                btnSubmit.isEnabled = false
                textLayout.setHelperTextColor(colorStateList)
            }

            override fun onFinish() {
                textLayout.isHelperTextEnabled = false
                btnSubmit.isEnabled = true
            }
        }
        return countDownTimer
    }
}