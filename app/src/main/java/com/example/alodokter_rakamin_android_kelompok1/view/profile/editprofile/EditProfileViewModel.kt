package com.example.alodokter_rakamin_android_kelompok1.view.profile.editprofile

import android.content.Context
import android.text.Editable
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.data.repository.UserRepository
import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse
import org.json.JSONObject
import java.util.*

class EditProfileViewModel : ViewModel(){
    private val _isButtonEnabled : MutableLiveData<Boolean> = MutableLiveData(false)
    val isButtonEnabled : LiveData<Boolean> = _isButtonEnabled

    private val _isErrorEnabledEmail: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorEnabledEmail : LiveData<Boolean> = _isErrorEnabledEmail

    private val _textErrorEmail: MutableLiveData<String?> = MutableLiveData()
    val textErrorEmail: LiveData<String?> = _textErrorEmail

    private val _isErrorEnabledFullName: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorEnabledFullName : LiveData<Boolean> = _isErrorEnabledFullName

    private val _textErrorFullName: MutableLiveData<String?> = MutableLiveData()
    val textErrorFullName: LiveData<String?> = _textErrorFullName

    private val _isErrorEnabledDate: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorEnabledDate : LiveData<Boolean> = _isErrorEnabledDate

    private val _textErrorDate: MutableLiveData<String?> = MutableLiveData()
    val textErrorDate: LiveData<String?> = _textErrorDate

    private val _isErrorEnabledPhoneNumber: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorEnabledPhoneNumber : LiveData<Boolean> = _isErrorEnabledPhoneNumber

    private val _textErrorPhoneNumber: MutableLiveData<String?> = MutableLiveData()
    val textErrorPhoneNumber: LiveData<String?> = _textErrorPhoneNumber

    private var isEmail = false
    private var isFullName = false
    private var isDate = false
    private var isPhoneNumber = false
    private lateinit var repository: UserRepository
    private var user : JSONObject = JSONObject()
    private var email = ""
    private var fullName = ""
    private var date = ""
    private var phoneNumber = ""
    private var age = 0
    private var id = 0
    private var isChange = false

    fun editProfile(context: Context):MutableLiveData<ApiResponse<UserResponse>> {
        checkAge()
        putUser()
        isChange = false
        isEmail = false
        isFullName = false
        isDate = false
        isPhoneNumber = false
        checkBtn()
        return repository.editUser(context,id,user)
    }

    fun setRepository(userRepository: UserRepository){
        repository = userRepository
    }

    fun setUser(userResponse: UserResponse) {
        id = userResponse.user?.id!!
        email = userResponse.user.email
        fullName = userResponse.user.full_name
        date = userResponse.user.birth_date
        phoneNumber = userResponse.user.phone_number.toString()
        age = userResponse.user.age
    }

    private fun putUser(){
        user.put("full_name",fullName)
        user.put("email",email)
        user.put("birth_date",date)
        user.put("phone_number",phoneNumber)
        user.put("age",age)
    }


    fun afterTextChangeFullName(it: Editable?, errorText: String){
        if(it.toString().isNotEmpty() && (it.toString().trim() != fullName)){
            isFullName = true
            _textErrorFullName.value = null
            _isErrorEnabledFullName.value = false
            fullName = it.toString().trim()
            isChange = true
        } else if(it.toString().trim() == fullName && !isChange){
            isFullName = false
        } else {
            isFullName = false
            _textErrorFullName.value = errorText
            _isErrorEnabledFullName.value = true
            fullName = ""
        }
        checkBtn()
    }

    fun afterTextChangeDate(it: Editable?, errorText: String){
        if(it.toString().isNotEmpty() && it.toString().trim() != date ){
            _isErrorEnabledDate.value = false
            _textErrorDate.value = null
            isDate = true
            date = it.toString().trim()
            isChange = true
        } else if(it.toString().trim() == date && !isChange){
            isDate = false
        } else {
            isDate = false
            _textErrorDate.value = errorText
            _isErrorEnabledDate.value = true
            date = ""
        }
        checkBtn()
    }

    fun afterTextChangePhoneNumber(it: Editable?, errorText: String){
        if(it.toString().isNotEmpty() && it.toString().trim() != phoneNumber){
            _isErrorEnabledPhoneNumber.value = false
            _textErrorPhoneNumber.value = null
            isPhoneNumber = true
            phoneNumber = it.toString().trim()
            isChange = true
        } else if(it.toString().trim() == phoneNumber && !isChange){
            isPhoneNumber = false
        } else {
            isPhoneNumber = false
            _textErrorPhoneNumber.value = errorText
            _isErrorEnabledPhoneNumber.value = true
            phoneNumber = ""
        }
        checkBtn()
    }

    fun afterTextChangeEmail(it: Editable?, errorText: String){
        if(Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches() && it.toString().trim() != email){
            _isErrorEnabledEmail.value = false
            _textErrorEmail.value = null
            isEmail = true
            email = it.toString().trim()
            isChange = true
        } else if(it.toString().trim() == email && !isChange){
            isEmail = false
        } else {
            isEmail = false
            _textErrorEmail.value = errorText
            _isErrorEnabledEmail.value = true
            email = ""
        }
        checkBtn()
    }

    private fun checkAge(){
        val birthDate = date.split("-").map{
            it.toInt()
        }
        val birth = Calendar.getInstance()
        birth.set(Calendar.YEAR,birthDate[2])
        birth.set(Calendar.MONTH,birthDate[1])
        birth.set(Calendar.DAY_OF_MONTH,birthDate[0])
        val current = Calendar.getInstance()
        age = current.get(Calendar.YEAR) - birth.get(Calendar.YEAR)
        if (current.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
    }

    private fun checkBtn(){
        Log.d("1425a",isEmail.toString())
        Log.d("1425b",isDate.toString())
        Log.d("1425c",isFullName.toString())
        Log.d("1425d",isPhoneNumber.toString())
        Log.d("1425e",isChange.toString())
        if(isChange){
            isEmail = true
            isDate = true
            isFullName = true
            isPhoneNumber = true
        } else {
            isEmail = false
            isDate = false
            isFullName = false
            isPhoneNumber = false
        }
        _isButtonEnabled.value = isEmail && isDate && isFullName && isPhoneNumber && isChange
    }
}