package com.example.alodokter_rakamin_android_kelompok1.view.home

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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.adapter.HomeArticleAdapter
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.example.alodokter_rakamin_android_kelompok1.config.hide
import com.example.alodokter_rakamin_android_kelompok1.config.show
import com.example.alodokter_rakamin_android_kelompok1.data.entity.ArticleEntity
import com.example.alodokter_rakamin_android_kelompok1.data.repository.ArticleRepository
import com.example.alodokter_rakamin_android_kelompok1.data.repository.UserRepository
import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse
import com.example.alodokter_rakamin_android_kelompok1.databinding.HomeFragmentBinding
import com.example.alodokter_rakamin_android_kelompok1.view.article.ArticleListActivity
import com.example.alodokter_rakamin_android_kelompok1.view.profile.myprofile.MyProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var adapter: HomeArticleAdapter
    private val data = ArrayList<ArticleEntity>()
    private lateinit var user: UserResponse
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        init()
        binding.rvArtikel.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvArtikel.adapter = adapter
        navController = findNavController()

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
        binding.tvAllArticles.setOnClickListener {
            val intent = Intent(context, ArticleListActivity::class.java)
            startActivity(intent)
        }
        binding.svArtikel.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(searchArticle: String?): Boolean {
                val searchText = searchArticle!!
                if (searchText.isNotEmpty()){
                    val intent = Intent(context, ArticleListActivity::class.java)
                    intent.putExtra(ArticleListActivity.QUERY_STRING,searchText)
                    startActivity(intent)
                }
                return false
            }

            override fun onQueryTextChange(searchArticle: String?): Boolean {
                return false
            }
        })
    }

    private fun init(){
        val token = SharedPreferences(requireContext()).getUserToken()
        viewModel.setRepository(ArticleRepository())
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
                }
                is ApiResponse.Error -> {
                    val snackBar = Snackbar.make(binding.parentHomeLayout, it.error, Snackbar.LENGTH_LONG)
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
        viewModel.getAllArticles().observe(viewLifecycleOwner){
            when(it){
                is ApiResponse.Success -> {
                    binding.loading.hide()
                    val articles = it.data
                    if (!articles.data.isNullOrEmpty()){
                        data.addAll(articles.data.filterNotNull())
                        binding.rvArtikel.show()
                        adapter.setData(data)

                    } else {
                        binding.rvArtikel.hide()
                    }
                }
                is ApiResponse.Error -> {
                    val snackbar = Snackbar.make(binding.parentHomeLayout, it.error, Snackbar.LENGTH_LONG)
                    snackbar.setBackgroundTint(ContextCompat.getColor(requireContext()
                        , R.color.error_red))
                    snackbar.show()
                    binding.loading.hide()
                }
                is ApiResponse.Loading -> {
                    binding.loading.show()
                }
            }
        }

        adapter = HomeArticleAdapter(data)
        binding.btnSeeDoctors.setOnClickListener {
            if(SharedPreferences(requireContext()).getLoggedStatus()){
                val navView: BottomNavigationView = activity?.findViewById(R.id.nav_view) as BottomNavigationView
                navView.visibility = View.VISIBLE
                navController.navigate(R.id.navigation_consult)
            } else {
                val navView: BottomNavigationView = activity?.findViewById(R.id.nav_view) as BottomNavigationView
                navView.visibility = View.GONE
                navController.navigate(R.id.loginFragment)
            }
        }
    }


}