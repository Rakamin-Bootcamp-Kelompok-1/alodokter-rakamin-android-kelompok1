package com.example.alodokter_rakamin_android_kelompok1.view.booking.confirm

import androidx.lifecycle.ViewModel
import com.example.alodokter_rakamin_android_kelompok1.data.repository.PatientRepository

class ConfirmBookingViewModel : ViewModel() {

    private lateinit var repository: PatientRepository

    fun getPatient(id:Int) = repository.getPatientsByUser(1,1,id)

    fun setRepository(repository: PatientRepository){
        this.repository = repository
    }
}