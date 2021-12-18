package com.example.alodokter_rakamin_android_kelompok1.view.booking

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.adapter.DoctorAdapter
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.example.alodokter_rakamin_android_kelompok1.config.hide
import com.example.alodokter_rakamin_android_kelompok1.config.show
import com.example.alodokter_rakamin_android_kelompok1.data.entity.DoctorEntity
import com.example.alodokter_rakamin_android_kelompok1.data.repository.DoctorRepository
import com.example.alodokter_rakamin_android_kelompok1.data.repository.UserRepository
import com.example.alodokter_rakamin_android_kelompok1.data.response.DataResponse
import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse
import com.example.alodokter_rakamin_android_kelompok1.databinding.BookingFragmentBinding
import com.example.alodokter_rakamin_android_kelompok1.view.profile.myprofile.MyProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class BookingFragment : Fragment(),DoctorAdapter.OnLoadMoreListener {

    private lateinit var viewModel: BookingViewModel
    private lateinit var binding: BookingFragmentBinding
    private lateinit var doctorAdapter : DoctorAdapter
    private val data = ArrayList<DoctorEntity>()
    private lateinit var user: UserResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BookingFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[BookingViewModel::class.java]

        init()
        // Adapter for Speciality




        binding.svDoctor.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchDoctors: String?): Boolean {
                data.clear()
                val searchText = searchDoctors!!
                if (searchText.isNotEmpty()){
                    viewModel.resetData()
                    doctorAdapter.resetData()
                    viewModel.searchArticles(query = searchText).observe(this@BookingFragment.viewLifecycleOwner){
                        getData(it)
                    }
                }
                return false
            }

            override fun onQueryTextChange(searchDoctors: String?): Boolean {
                if(searchDoctors.isNullOrEmpty()){
                    viewModel.getAllDoctors().observe(this@BookingFragment.viewLifecycleOwner){
                        viewModel.resetData()
                        doctorAdapter.resetData()
                        getData(it)
                    }
                }
                return false
            }
        })

    }

    private fun init(){
        val navController = findNavController()
        val token = SharedPreferences(requireContext()).getUserToken()
        viewModel.setRepository(DoctorRepository())
        viewModel.setUserRepository(UserRepository())
        viewModel.getAllDoctors().observe(viewLifecycleOwner){
            getData(it)
        }
        viewModel.getUser(token as String).observe(viewLifecycleOwner){
            when(it){
                is ApiResponse.Success -> {
                    binding.loading.hide()
                    user = it.data
                    Log.d("1422",user.toString())
                    when(user.user?.image_path){
                        null -> {
                            Glide.with(this)
                                .load(R.drawable.shawn_doctor)
                                .apply(RequestOptions.circleCropTransform())
                                .into(binding.toolbarProfile.ivProfile)
                        }
                        else -> {
                            Glide.with(this)
                                .load(user.user!!.image_path)
                                .error(R.drawable.shawn_doctor)
                                .apply(RequestOptions.circleCropTransform())
                                .into(binding.toolbarProfile.ivProfile)
                        }
                    }
                    binding.toolbarProfile.tvName.text = user.user?.full_name
                    binding.toolbarProfile.ivProfile.setOnClickListener {
                        if(SharedPreferences(requireContext()).getLoggedStatus()){
                            val intent = Intent(requireContext(), MyProfileActivity::class.java)
                            val jsonString = Gson().toJson(user)
                            intent.putExtra(MyProfileActivity.MY_PROFILE_ACTIVITY,jsonString)
                            startActivity(intent)
                        } else {
                            val navView: BottomNavigationView = activity?.findViewById(R.id.nav_view) as BottomNavigationView
                            navView.visibility = View.GONE
                            navController.navigate(R.id.loginFragment)
                        }
                    }
                }
                is ApiResponse.Error -> {
                    val snackBar = Snackbar.make(binding.parentBookingLayout, it.error, Snackbar.LENGTH_LONG)
                    snackBar.setBackgroundTint(ContextCompat.getColor(requireContext()
                        , R.color.error_red))
                    snackBar.show()
                    binding.loading.hide()
                }
                is ApiResponse.Loading -> {
                    binding.loading.show()
                }
            }
        }
        binding.rvDoctor.layoutManager = LinearLayoutManager(requireContext())
        doctorAdapter = DoctorAdapter(binding.rvDoctor)
        doctorAdapter.onLoadMoreListener = this
        binding.rvDoctor.adapter = doctorAdapter
    }

    override fun onLoadMore() {
        viewModel.loadNewDoctors().observe(this){
            getData(it)
        }
    }

    private fun getData(it: ApiResponse<DataResponse<DoctorEntity>>){
        when(it) {
            is ApiResponse.Success -> {
                binding.loading.hide()
                val doctors = it.data
                viewModel.setPage(doctors.meta)
                if (!doctors.data.isNullOrEmpty()){
                    data.clear()
                    data.addAll(doctors.data.filterNotNull())
                    binding.rvDoctor.show()
                    binding.tvNotFound.hide()
                    doctorAdapter.setLoad()
                    doctorAdapter.setData(data)
                } else {
                    binding.tvNotFound.show()
                    binding.rvDoctor.hide()
                }
            }
            is ApiResponse.Error -> {
                val snackbar = Snackbar.make(binding.parentBookingLayout, it.error, Snackbar.LENGTH_LONG)
                snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.error_red))
                snackbar.show()
                binding.loading.hide()
                binding.tvNotFound.show()
                binding.rvDoctor.hide()
            }
            is ApiResponse.Loading -> {
                binding.loading.show()
                binding.tvNotFound.hide()
                binding.rvDoctor.hide()
            }
        }
    }

}