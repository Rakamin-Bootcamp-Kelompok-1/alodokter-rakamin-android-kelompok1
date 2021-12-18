package com.example.alodokter_rakamin_android_kelompok1.view.booking.confirm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.example.alodokter_rakamin_android_kelompok1.config.hide
import com.example.alodokter_rakamin_android_kelompok1.config.show
import com.example.alodokter_rakamin_android_kelompok1.data.repository.PatientRepository
import com.example.alodokter_rakamin_android_kelompok1.databinding.ActivityConfirmBookingBinding
import com.google.android.material.snackbar.Snackbar

class ConfirmBookingActivity : AppCompatActivity() {

    companion object {
        const val DOCTOR_NAME = "doctor_name"
        const val DOCTOR_IMAGE = "doctor_image"
        const val DOCTOR_RATING = "doctor_rating"
        const val DOCTOR_LOCATION = "doctor_location"
        const val DOCTOR_SPECIALITY = "doctor_speciality"
        const val DOCTOR_PRICE_RATE = "price_rate"
        const val DOCTOR_SCHEDULE = "doctor_schedule"
    }

    private lateinit var binding: ActivityConfirmBookingBinding
    private lateinit var viewModel: ConfirmBookingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ConfirmBookingViewModel::class.java]

        val name = intent.getStringExtra(DOCTOR_NAME)
        val image_url = intent.getStringExtra(DOCTOR_IMAGE)
        val rating = intent.getStringExtra(DOCTOR_RATING)
        val location = intent.getStringExtra(DOCTOR_LOCATION)
        val speciality = intent.getStringExtra(DOCTOR_SPECIALITY)
        val price = intent.getIntExtra(DOCTOR_PRICE_RATE, 0)

        initPatient()

        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transform(RoundedCorners(50))
        Glide.with(this)
            .load(image_url)
            .fitCenter()
            .apply(requestOptions)
            .error(R.drawable.shawn_doctor)
            .into(binding.ivDoctor)
        binding.tvDoctorName.text = name
        binding.tvSpecialist.text = speciality
        binding.tvLoc.text = location
        binding.tvRupiah.text = price.toString()
        binding.tvRating.text = rating

    }

    private fun initCalender(){

    }

    private fun initPatient(){
        binding.loading.hide()
        val userId = SharedPreferences(this).getUserId()
        viewModel.setRepository(PatientRepository())
        // if intent patient == null
        viewModel.getPatient(userId).observe(this){
            when(it){
                is ApiResponse.Success -> {
                    binding.loading.hide()
                    val data = it.data
                    if(!data.data.isNullOrEmpty()){
                        binding.tvEmpty.hide()
                        binding.lin2.show()
                        binding.tvName.text = data.data[0]?.patient_name
                        binding.tvGender.text = data.data[0]?.gender
                        binding.tvTanggalLahir.text = data.data[0]?.birth_date
                        binding.tvPatientUmur.text = data.data[0]?.age.toString()
                        binding.tvGender.text = data.data[0]?.gender
                        binding.tvStatus.text = data.data[0]?.status
                    } else {
                        binding.lin2.hide()
                        binding.tvEmpty.show()
                    }
                }
                is ApiResponse.Error -> {
                    val snackBar = Snackbar.make(binding.parent, it.error, Snackbar.LENGTH_LONG)
                    snackBar.setBackgroundTint(
                        ContextCompat.getColor(this
                        , R.color.error_red))
                    snackBar.show()
                    binding.loading.hide()
                }
                is ApiResponse.Loading -> {
                    binding.loading.show()
                }
            }
        }
        // else intent != null
    }
}