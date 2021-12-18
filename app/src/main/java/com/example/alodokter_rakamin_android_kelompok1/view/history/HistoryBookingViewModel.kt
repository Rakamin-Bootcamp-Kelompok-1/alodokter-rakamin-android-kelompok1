package com.example.alodokter_rakamin_android_kelompok1.view.history

import androidx.lifecycle.ViewModel
import com.example.alodokter_rakamin_android_kelompok1.data.repository.UserRepository

class HistoryBookingViewModel : ViewModel() {
    private lateinit var userRepository: UserRepository


    fun setUserRepository(userRepository: UserRepository){
        this.userRepository = userRepository
    }

    fun getUser(token: String) = userRepository.getUser(token)
}