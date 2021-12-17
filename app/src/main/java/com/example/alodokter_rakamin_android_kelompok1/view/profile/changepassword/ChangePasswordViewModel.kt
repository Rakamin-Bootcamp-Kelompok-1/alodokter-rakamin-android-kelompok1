package com.example.alodokter_rakamin_android_kelompok1.view.profile.changepassword

import android.content.Context
import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.data.repository.UserRepository
import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse
import org.json.JSONObject

class ChangePasswordViewModel: ViewModel() {
    private val _isButtonEnabled : MutableLiveData<Boolean> = MutableLiveData(false)
    val isButtonEnabled : LiveData<Boolean> = _isButtonEnabled

    private val _isErrorEnabledOldPassword: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorEnabledOldPassword : LiveData<Boolean> = _isErrorEnabledOldPassword

    private val _textErrorOldPassword: MutableLiveData<String?> = MutableLiveData()
    val textErrorOldPassword: LiveData<String?> = _textErrorOldPassword

    private val _isErrorEnabledNewPassword: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorEnabledNewPassword : LiveData<Boolean> = _isErrorEnabledNewPassword

    private val _textErrorNewPassword: MutableLiveData<String?> = MutableLiveData()
    val textErrorNewPassword: LiveData<String?> = _textErrorNewPassword

    private val _isErrorEnabledConfirmPassword: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorEnabledConfirmPassword : LiveData<Boolean> = _isErrorEnabledConfirmPassword

    private val _textErrorConfirmPassword: MutableLiveData<String?> = MutableLiveData()
    val textErrorConfirmPassword: LiveData<String?> = _textErrorConfirmPassword

    private var isNewPassword = false
    private var isOldPassword = false
    private var isConfirmPassword = false
    private var user : JSONObject = JSONObject()
    private lateinit var repository: UserRepository
    private var password = ""
    private var id = 0
    private var passwordDigest = ""
    private var oldPassword = ""

    fun editPassword(context: Context):MutableLiveData<ApiResponse<UserResponse>> {
        putUser()
        return repository.editUser(context,id,user)
    }

    fun setRepository(userRepository: UserRepository){
        repository = userRepository
    }

    fun setUser(userResponse: UserResponse) {
        id = userResponse.user?.id!!
        passwordDigest = userResponse.user.password_digest
    }

    private fun putUser(){
        user.put("password",password)
    }

    fun checkPassword(): Boolean{
        val result = BCrypt.verifyer().verify(oldPassword.toCharArray(),passwordDigest)
        var check = false
        if(result.verified) check = true
        return check
    }

    fun afterTextChangeOldPassword(it: Editable?, errorText: String){
        if(it.toString().length < 2) {
            isOldPassword = false
            _textErrorOldPassword.value = errorText
            _isErrorEnabledOldPassword.value = true
        } else {
            isOldPassword = true
            _textErrorOldPassword.value = null
            _isErrorEnabledOldPassword.value = false
            oldPassword = it.toString()
        }
        checkBtn()
    }

    fun afterTextChangeNewPassword(it: Editable?, errorText: String){
        if(it.toString().length > 1){
            isNewPassword = true
            _textErrorNewPassword.value = null
            _isErrorEnabledNewPassword.value = false
            password = it.toString().trim()
        } else {
            isNewPassword = false
            _textErrorNewPassword.value = errorText
            _isErrorEnabledNewPassword.value = true
            password = ""
        }
        checkBtn()
    }

    fun afterTextChangeConfirmPassword(it: Editable?, errorText: String){
        if(it.toString() == password){
            isConfirmPassword = true
            _textErrorConfirmPassword.value = null
            _isErrorEnabledConfirmPassword.value = false
        } else {
            isConfirmPassword = false
            _textErrorConfirmPassword.value = errorText
            _isErrorEnabledConfirmPassword.value = true
        }
        checkBtn()
    }

    private fun checkBtn(){
        _isButtonEnabled.value = isNewPassword && isConfirmPassword && isOldPassword
    }
}