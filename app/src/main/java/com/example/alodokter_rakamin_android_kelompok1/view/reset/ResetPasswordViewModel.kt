package com.example.alodokter_rakamin_android_kelompok1.view.reset

import android.os.CountDownTimer
import android.text.Editable
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResetPasswordViewModel : ViewModel() {

    private val _isButtonEnabled : MutableLiveData<Boolean> = MutableLiveData(false)
    val isButtonEnabled : LiveData<Boolean> = _isButtonEnabled

    private val _isHelperEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    val isHelperEnabled : LiveData<Boolean> = _isHelperEnabled

    private val _isErrorEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorEnabled : LiveData<Boolean> = _isErrorEnabled

    private val _countdownTimer: MutableLiveData<Int> = MutableLiveData(0)
    val countdownTimer : LiveData<Int> = _countdownTimer

    private val _textError: MutableLiveData<String?> = MutableLiveData()
    val textError: LiveData<String?> = _textError

    private val _isResend: MutableLiveData<Boolean> = MutableLiveData(false)
    val isResend: LiveData<Boolean> = _isResend

    private var email: String = ""

    fun sendEmail(){
        viewModelScope.launch(Dispatchers.IO) {
            //auth
        }
    }

    fun afterTextChange(it: Editable?, errorText: String){
        if(Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()){
            _isButtonEnabled.value = true
            _textError.value = null
            _isErrorEnabled.value = false
            email = it.toString().trim()
        } else {
            _isButtonEnabled.value = false
            _textError.value = errorText
            _isErrorEnabled.value = true
            email = ""
        }
    }

    fun countDown() : CountDownTimer {
        val countDownTimer = object : CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                _countdownTimer.value = millisUntilFinished.toInt()
                _isHelperEnabled.value = true
                _isButtonEnabled.value = false
                _isResend.value = true
            }

            override fun onFinish() {
                _isHelperEnabled.value = false
                _isButtonEnabled.value = true
                _isResend.value = false
            }
        }
        return countDownTimer
    }
}