package com.example.alodokter_rakamin_android_kelompok1.view.booking

import androidx.lifecycle.ViewModel
import com.example.alodokter_rakamin_android_kelompok1.data.repository.DoctorRepository

class BookingViewModel : ViewModel() {
    private lateinit var repository: DoctorRepository

    fun getAllDoctors() = repository.getDoctors(1, 10)

    fun setRepository(doctorRepository: DoctorRepository){
        repository = doctorRepository
    }
}