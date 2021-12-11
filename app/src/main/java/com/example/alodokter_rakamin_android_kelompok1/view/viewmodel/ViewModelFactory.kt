package com.example.alodokter_rakamin_android_kelompok1.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alodokter_rakamin_android_kelompok1.data.UserRepository
import com.example.alodokter_rakamin_android_kelompok1.view.login.LoginViewModel

class ViewModelFactory(): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(UserRepository()) as T
        }
        else throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}