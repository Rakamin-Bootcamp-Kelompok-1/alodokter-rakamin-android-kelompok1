package com.example.alodokter_rakamin_android_kelompok1.view.booking

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.adapter.DoctorAdapter
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.config.hide
import com.example.alodokter_rakamin_android_kelompok1.config.show
import com.example.alodokter_rakamin_android_kelompok1.data.entity.DoctorEntity
import com.example.alodokter_rakamin_android_kelompok1.data.repository.DoctorRepository
import com.example.alodokter_rakamin_android_kelompok1.databinding.BookingFragmentBinding
import com.example.alodokter_rakamin_android_kelompok1.view.profile.myprofile.MyProfileActivity
import com.google.android.material.snackbar.Snackbar

class BookingFragment : Fragment() {

    private lateinit var viewModel: BookingViewModel
    private lateinit var binding: BookingFragmentBinding
    private lateinit var doctorAdapter : DoctorAdapter
    private val data = ArrayList<DoctorEntity>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BookingFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[BookingViewModel::class.java]

        init()
        // Adapter for Doctor
        binding.rvDoctor.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDoctor.adapter = doctorAdapter

        // Adapter for Speciality


        binding.toolbarProfile.ivProfile.setOnClickListener {
//            val intent = Intent(requireContext(), MyProfileActivity::class.java)
//            startActivity(intent)
        }
    }

    fun init(){
        viewModel.setRepository(DoctorRepository())
        viewModel.getAllDoctors().observe(viewLifecycleOwner){
            when(it){
                is ApiResponse.Success -> {
                    binding.loading.hide()
                    val doctors = it.data
                    if (doctors != null){
                        data.addAll(doctors.data.filterNotNull())
                        binding.rvDoctor.show()
                        doctorAdapter.setData(data)
                    } else {
                        binding.rvDoctor.hide()
                    }
                }
                is ApiResponse.Error -> {
                    val snackbar = Snackbar.make(binding.parentBookingLayout, it.error, Snackbar.LENGTH_LONG)
                    snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.error_red))
                    snackbar.show()
                    binding.loading.hide()
                }
                is ApiResponse.Loading -> {
                    binding.loading.show()
                }
            }
        }
        doctorAdapter = DoctorAdapter(data)
    }

}