package com.example.alodokter_rakamin_android_kelompok1.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.alodokter_rakamin_android_kelompok1.data.UserRepository
import com.example.alodokter_rakamin_android_kelompok1.data.entity.LoginEntity

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {
    var loginEntity = LoginEntity(email = String(), password_digest = String())

    fun getLogin(): LiveData<LoginEntity> {
        return userRepository.getLogin(loginEntity)
    }
}