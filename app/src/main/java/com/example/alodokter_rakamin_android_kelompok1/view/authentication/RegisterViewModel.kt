package com.example.alodokter_rakamin_android_kelompok1.view.authentication

import android.text.Editable
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alodokter_rakamin_android_kelompok1.data.repository.AuthRepository
import com.example.alodokter_rakamin_android_kelompok1.data.entity.RegisterEntity
import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse

class RegisterViewModel : ViewModel(){

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

    private val _isErrorEnabledConfirmPassword: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorEnabledConfirmPassword : LiveData<Boolean> = _isErrorEnabledConfirmPassword

    private val _textErrorConfirmPassword: MutableLiveData<String?> = MutableLiveData()
    val textErrorConfirmPassword: LiveData<String?> = _textErrorConfirmPassword

    private val _isErrorEnabledFullName: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorEnabledFullName : LiveData<Boolean> = _isErrorEnabledFullName

    private val _textErrorFullName: MutableLiveData<String?> = MutableLiveData()
    val textErrorFullName: LiveData<String?> = _textErrorFullName

    private val _isErrorEnabledGender: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorEnabledGender : LiveData<Boolean> = _isErrorEnabledGender

    private val _textErrorGender: MutableLiveData<String?> = MutableLiveData()
    val textErrorGender: LiveData<String?> = _textErrorGender

    private val _isErrorEnabledDate: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorEnabledDate : LiveData<Boolean> = _isErrorEnabledDate

    private val _textErrorDate: MutableLiveData<String?> = MutableLiveData()
    val textErrorDate: LiveData<String?> = _textErrorDate

    private val _isErrorEnabledPhoneNumber: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorEnabledPhoneNumber : LiveData<Boolean> = _isErrorEnabledPhoneNumber

    private val _textErrorPhoneNumber: MutableLiveData<String?> = MutableLiveData()
    val textErrorPhoneNumber: LiveData<String?> = _textErrorPhoneNumber

    private var isEmail = false
    private var isPassword = false
    private var isConfirmPassword = false
    private var isFullname = false
    private var isDate = false
    private var isPhoneNumber = false
    private var isGender = false
    private lateinit var repository: AuthRepository
    private var email = ""
    private var password = ""
    private var fullName = ""
    private var date = ""
    private var phoneNumber = ""
    private var gender = ""

    fun getRegister() = repository.getRegister(RegisterEntity(
        full_name = fullName,
        password_digest = password,
        email = email,
        birth_date = date,
        gender = gender,
        phone_number = phoneNumber
    ))

    fun setRepository(authRepository: AuthRepository){
        repository = authRepository
    }

    fun afterTextChangeFullName(it: Editable?, errorText: String){
        if(it.toString().isNotEmpty()){
            isFullname = true
            _textErrorFullName.value = null
            _isErrorEnabledFullName.value = false
            fullName = it.toString().trim()
        } else {
            isFullname = false
            _textErrorFullName.value = errorText
            _isErrorEnabledFullName.value = true
            fullName = ""
        }
        checkBtn()
    }

    fun afterTextChangeDate(it: Editable?, errorText: String){
        if(it.toString().isNotEmpty()){
            _isErrorEnabledDate.value = false
            _textErrorDate.value = null
            isDate = true
            date = it.toString().trim()
        } else {
            isDate = false
            _textErrorDate.value = errorText
            _isErrorEnabledDate.value = true
            date = ""
        }
        checkBtn()
    }

    fun afterTextChangePhoneNumber(it: Editable?, errorText: String){
        if(it.toString().isNotEmpty()){
            _isErrorEnabledPhoneNumber.value = false
            _textErrorPhoneNumber.value = null
            isPhoneNumber = true
            phoneNumber = it.toString().trim()
        } else {
            isPhoneNumber = false
            _textErrorPhoneNumber.value = errorText
            _isErrorEnabledPhoneNumber.value = true
            phoneNumber = ""
        }
        checkBtn()
    }

    fun afterTextChangeGender(it: Editable?, errorText: String){
        if(it.toString().isNotEmpty()){
            _isErrorEnabledGender.value = false
            _textErrorGender.value = null
            isGender = true
            gender = it.toString().trim()
        } else {
            isGender = false
            _textErrorGender.value = errorText
            _isErrorEnabledGender.value = true
            gender = ""
        }
        checkBtn()
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
        _isButtonEnabled.value = isEmail && isPassword && isConfirmPassword && isDate && isGender && isFullname && isPhoneNumber
    }
}