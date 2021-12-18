package com.example.alodokter_rakamin_android_kelompok1.view.booking.confirm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.adapter.ConfirmBookingAdapter
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.example.alodokter_rakamin_android_kelompok1.config.hide
import com.example.alodokter_rakamin_android_kelompok1.config.show
import com.example.alodokter_rakamin_android_kelompok1.data.entity.CalendarEntity
import com.example.alodokter_rakamin_android_kelompok1.data.repository.PatientRepository
import com.example.alodokter_rakamin_android_kelompok1.databinding.ActivityConfirmBookingBinding
import com.example.alodokter_rakamin_android_kelompok1.view.booking.SuccessBookingActivity
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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

    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()
    private lateinit var confirmAdapter: ConfirmBookingAdapter
    private val calendarList2 = ArrayList<CalendarEntity>()
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
        setUpAdapter()
        setUpClickListener()
        setUpCalendar()

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

        binding.ibBack.setOnClickListener {
            finish()
        }

        binding.btnConfirm.setOnClickListener {
            val intent = Intent(this, SuccessBookingActivity::class.java)
            startActivity(intent)
        }

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

    /**
     * Set up click listener
     */
    private fun setUpClickListener() {
        binding.ivCalendarNext.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
        binding.ivCalendarPrevious.setOnClickListener {
            cal.add(Calendar.MONTH, -1)
            if (cal == currentDate)
                setUpCalendar()
            else
                setUpCalendar()
        }
    }

    /**
     * Setting up adapter for recyclerview
     */
    private fun setUpAdapter() {
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing_10)
        binding.rvAtur.addItemDecoration(CalendarItemDecoration(spacingInPixels))
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvAtur)
        confirmAdapter = ConfirmBookingAdapter { _: CalendarEntity, position: Int ->
            calendarList2.forEachIndexed { index, calendarModel ->
                calendarModel.isSelected = index == position
            }
            confirmAdapter.setData(calendarList2)
        }
        binding.rvAtur.adapter = confirmAdapter
    }

    /**
     * Function to setup calendar for every month
     */
    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarEntity>()
        binding.tvDateMonth.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        while (dates.size < maxDaysInMonth) {
            dates.add(monthCalendar.time)
            calendarList.add(CalendarEntity(monthCalendar.time))
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        calendarList2.clear()
        calendarList2.addAll(calendarList)
        confirmAdapter.setData(calendarList)
    }

}