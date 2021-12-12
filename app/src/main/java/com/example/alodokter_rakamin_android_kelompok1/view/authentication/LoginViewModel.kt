package com.example.alodokter_rakamin_android_kelompok1.view.authentication

import android.text.Editable
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alodokter_rakamin_android_kelompok1.data.AuthRepository
import com.example.alodokter_rakamin_android_kelompok1.data.entity.LoginEntity


class LoginViewModel : ViewModel() {

    private val _isButtonEnabled : MutableLiveData<Boolean> = MutableLiveData(false)
    val isButtonEnabled : LiveData<Boolean> = _isButtonEnabled

    private val _isErrorEnabledEmail: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorEnabledEmail : LiveData<Boolean> = _isErrorEnabledEmail

    private val _textErrorEmail: MutableLiveData<String?> = MutableLiveData()
    val textErrorEmail: LiveData<String?> = _textErrorEmail

    private val _isErrorEnabledPassword: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorEnabledPassword : LiveData<Boolean> = _isErrorEnabledPassword

    private val _textErrorPassword: MutableLiveData<String?> = MutableLiveData()
    val textErrorPassword: LiveData<String?> = _textErrorPassword

    private var isEmail = false
    private var isPassword = false
    private lateinit var repository: AuthRepository
    private var email = ""
    private var password = ""

    fun getLogin() = repository.getLogin(LoginEntity(email,password))

    fun setRepository(authRepository: AuthRepository){
        repository = authRepository
    }

    fun afterTextChangeEmail(it: Editable?, errorText: String){
        if(Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()){
            _isErrorEnabledEmail.value = false
            _textErrorEmail.value = null
            isEmail = true
            email = it.toString().trim()
        } else {
            isEmail = false
            _textErrorEmail.value = errorText
            _isErrorEnabledEmail.value = true
            email = ""
        }
        checkBtn()
    }

    fun afterTextChangePassword(it: Editable?, errorText: String){
        if(it.toString().length > 1){
            isPassword = true
            _textErrorPassword.value = null
            _isErrorEnabledPassword.value = false
            password = it.toString().trim()
        } else {
            isPassword = false
            _textErrorPassword.value = errorText
            _isErrorEnabledPassword.value = true
            password = ""
        }
        checkBtn()
    }

    private fun checkBtn(){
        _isButtonEnabled.value = isEmail && isPassword
    }
}