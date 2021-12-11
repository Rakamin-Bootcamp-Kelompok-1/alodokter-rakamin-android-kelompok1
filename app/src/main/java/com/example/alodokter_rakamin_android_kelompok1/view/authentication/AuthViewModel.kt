package com.example.alodokter_rakamin_android_kelompok1.view.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alodokter_rakamin_android_kelompok1.config.Resource
import com.example.alodokter_rakamin_android_kelompok1.data.AuthRepository
import com.example.alodokter_rakamin_android_kelompok1.data.entity.LoginEntity
import com.example.alodokter_rakamin_android_kelompok1.data.entity.RegisterEntity
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

}