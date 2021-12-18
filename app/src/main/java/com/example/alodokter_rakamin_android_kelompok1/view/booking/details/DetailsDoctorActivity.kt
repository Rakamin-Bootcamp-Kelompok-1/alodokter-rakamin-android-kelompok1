package com.example.alodokter_rakamin_android_kelompok1.view.booking.details

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.data.entity.DoctorEntity
import com.example.alodokter_rakamin_android_kelompok1.databinding.ActivityDetailsDoctorBinding
import com.example.alodokter_rakamin_android_kelompok1.view.booking.confirm.ConfirmBookingActivity
import kotlinx.android.synthetic.main.activity_details_doctor.*

class DetailsDoctorActivity : AppCompatActivity() {

    companion object {
        const val DOCTOR_NAME = "doctor_name"
        const val DOCTOR_IMAGE = "doctor_image"
        const val DOCTOR_RATING = "doctor_rating"
        const val DOCTOR_LOCATION = "doctor_location"
        const val DOCTOR_SPECIALITY = "doctor_speciality"
        const val DOCTOR_BIOGRAPHY = "doctor_bio"
        const val DOCTOR_EDUCATION = "doctor_edu"
        const val DOCTOR_PRICE_RATE = "price_rate"
        const val DOCTOR_SCHEDULE = "doctor_schedule"
    }

    private lateinit var binding: ActivityDetailsDoctorBinding
    private lateinit var viewModel: DetailsDoctorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[DetailsDoctorViewModel::class.java]

        val name = intent.getStringExtra(DOCTOR_NAME)
        val image_url = intent.getStringExtra(DOCTOR_IMAGE)
        val rating = intent.getStringExtra(DOCTOR_RATING)
        val location = intent.getStringExtra(DOCTOR_LOCATION)
        val speciality = intent.getStringExtra(DOCTOR_SPECIALITY)
        val education = intent.getStringExtra(DOCTOR_EDUCATION)
        val bio = intent.getStringExtra(DOCTOR_BIOGRAPHY)
        val price = intent.getIntExtra(DOCTOR_PRICE_RATE, 0)
        Log.v("1251", price.toString())

        setDoctor(name, image_url, rating, location, speciality, education, bio, price)
        binding.btnBooking.setOnClickListener {
            init(name, image_url, rating, location, speciality,price)
        }

        binding.ibBack.setOnClickListener {
            finish()
        }
    }


    fun setDoctor(
        name: String?,
        imageUrl: String?,
        rating: String?,
        loc: String?,
        speciality: String?,
        edu: String?,
        bio: String?,
        price: Int?){

        if (imageUrl != null){
            val image = Uri.parse(imageUrl)
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(RoundedCorners(50))
            Glide.with(this)
                .load(image)
                .fitCenter()
                .apply(requestOptions)
                .error(R.drawable.shawn_doctor)
                .into(ivDoctor as ImageView)
        } else {
            ivDoctor?.setImageResource(R.drawable.shawn_doctor)
        }

        binding.tvDoctorName.text = name
        binding.tvSpecialist.text = speciality
        binding.tvLoc.text = loc
        binding.tvRating.text = rating
        binding.tvBioDesc.text = bio
        binding.tvPrice.text = price.toString()
        binding.tvEducation.text = edu

    }

    fun init(name: String?,
             imageUrl: String?,
             rating: String?,
             loc: String?,
             speciality: String?,
             price: Int?){
        val intent = Intent(this@DetailsDoctorActivity, ConfirmBookingActivity::class.java)
        intent.putExtra(DOCTOR_NAME, name)
        intent.putExtra(DOCTOR_IMAGE, imageUrl)
        intent.putExtra(DOCTOR_RATING, rating)
        intent.putExtra(DOCTOR_LOCATION, loc)
        intent.putExtra(DOCTOR_SPECIALITY, speciality)
        intent.putExtra(DOCTOR_PRICE_RATE, price)
        startActivity(intent)
    }
}