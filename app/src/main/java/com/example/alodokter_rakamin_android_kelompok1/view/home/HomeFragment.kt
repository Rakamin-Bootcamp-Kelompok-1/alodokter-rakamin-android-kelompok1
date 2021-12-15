package com.example.alodokter_rakamin_android_kelompok1.view.home

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.adapter.ArticleAdapter
import com.example.alodokter_rakamin_android_kelompok1.adapter.HomeArticleAdapter
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.config.hide
import com.example.alodokter_rakamin_android_kelompok1.config.show
import com.example.alodokter_rakamin_android_kelompok1.data.entity.ArticleEntity
import com.example.alodokter_rakamin_android_kelompok1.data.repository.ArticleRepository
import com.example.alodokter_rakamin_android_kelompok1.databinding.HomeFragmentBinding
import com.example.alodokter_rakamin_android_kelompok1.view.article.ArticleListActivity
import com.example.alodokter_rakamin_android_kelompok1.view.reset.ResetPasswordActivity
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var adapter: HomeArticleAdapter
    private val data = ArrayList<ArticleEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        init()
        binding.rvArtikel.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvArtikel.adapter = adapter


        binding.tvAllArticles.setOnClickListener {
            val intent = Intent(context, ArticleListActivity::class.java)
            startActivity(intent)
        }
    }

    fun init(){
        viewModel.setRepository(ArticleRepository())
        viewModel.getAllArticles().observe(viewLifecycleOwner){
            when(it){
                is ApiResponse.Success -> {
                    binding.loading.hide()
                    val articles = it.data
                    if (articles != null){
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

    }


}