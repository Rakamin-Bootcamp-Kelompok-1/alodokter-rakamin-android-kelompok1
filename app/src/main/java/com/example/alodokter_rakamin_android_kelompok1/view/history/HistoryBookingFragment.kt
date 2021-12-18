package com.example.alodokter_rakamin_android_kelompok1.view.history

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.example.alodokter_rakamin_android_kelompok1.config.hide
import com.example.alodokter_rakamin_android_kelompok1.config.show
import com.example.alodokter_rakamin_android_kelompok1.data.repository.UserRepository
import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse
import com.example.alodokter_rakamin_android_kelompok1.databinding.HistoryBookingFragmentBinding
import com.example.alodokter_rakamin_android_kelompok1.view.profile.myprofile.MyProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class HistoryBookingFragment : Fragment() {

    private lateinit var binding: HistoryBookingFragmentBinding
    private lateinit var viewModel: HistoryBookingViewModel
    private lateinit var user: UserResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HistoryBookingFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HistoryBookingViewModel::class.java]
        init()

        binding.tvNotFound.show()




    }

    fun init(){
        val navController = findNavController()
        val token = SharedPreferences(requireContext()).getUserToken()
        viewModel.setUserRepository(UserRepository())
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
                    val snackBar = Snackbar.make(binding.parentHistoryLayout, it.error, Snackbar.LENGTH_LONG)
                    snackBar.setBackgroundTint(
                        ContextCompat.getColor(requireContext()
                        , R.color.error_red))
                    snackBar.show()
                    binding.loading.hide()
                }
                is ApiResponse.Loading -> {
                    binding.loading.show()
                }
            }
        }
    }

}