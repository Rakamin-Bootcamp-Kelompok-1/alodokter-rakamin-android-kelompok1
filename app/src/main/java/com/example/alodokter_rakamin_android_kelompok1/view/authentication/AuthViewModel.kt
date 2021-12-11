package com.example.alodokter_rakamin_android_kelompok1.view.authentication

import android.content.Context
import android.text.Editable
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alodokter_rakamin_android_kelompok1.config.Resource
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.example.alodokter_rakamin_android_kelompok1.data.AuthRepository
import com.example.alodokter_rakamin_android_kelompok1.data.entity.LoginEntity
import com.example.alodokter_rakamin_android_kelompok1.data.entity.RegisterEntity
import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

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

    private val _isError : MutableLiveData<String?> = MutableLiveData()
    val isError : LiveData<String?> = _isError

    private var isEmail = false
    private var isPassword = false
    private lateinit var repository: AuthRepository
    private var email = ""
    private var password = ""

    fun getLogin(context: Context) {
        val user = repository.getLogin(LoginEntity(email,password)).value
        val token = user?.token
        _isError.value = user?.error
        user?.token?.let { Log.d("TEST", it) }
        Log.d("TEST",_isError.value.toString())
        if(user?.error == null) user?.let { SharedPreferences(context).setUser(it,true) }
        viewModelScope.launch(Dispatchers.IO) {
            if (token != null) {
                SharedPreferences(context).saveAuthToken(token)
            }
        }
    }

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