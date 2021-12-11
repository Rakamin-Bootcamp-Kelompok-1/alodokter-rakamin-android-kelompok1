package com.example.alodokter_rakamin_android_kelompok1.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.alodokter_rakamin_android_kelompok1.data.UserRepository
import com.example.alodokter_rakamin_android_kelompok1.data.entity.LoginEntity
import com.example.alodokter_rakamin_android_kelompok1.data.entity.RegisterEntity


class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    private var registerEntity = RegisterEntity(
        full_name = String(),
        password_digest = String(),
        email = String(),
        gender = String(),
        birth_date = String(),
        phone_number = String()
    )

    fun getRegister(): LiveData<RegisterEntity> {
        return userRepository.getRegister(registerEntity)
    }
}