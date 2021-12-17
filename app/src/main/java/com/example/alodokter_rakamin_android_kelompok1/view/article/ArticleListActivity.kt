package com.example.alodokter_rakamin_android_kelompok1.view.article

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alodokter_rakamin_android_kelompok1.adapter.ArticleAdapter
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.config.hide
import com.example.alodokter_rakamin_android_kelompok1.config.show
import com.example.alodokter_rakamin_android_kelompok1.data.entity.ArticleEntity
import com.example.alodokter_rakamin_android_kelompok1.data.repository.ArticleRepository
import com.example.alodokter_rakamin_android_kelompok1.data.response.DataResponse
import com.example.alodokter_rakamin_android_kelompok1.databinding.ActivityArticelListBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList

class ArticleListActivity : AppCompatActivity(), ArticleAdapter.OnLoadMoreListener{

    lateinit var adapter: ArticleAdapter
    private lateinit var viewModel: ArticleViewModel
    private lateinit var binding: ActivityArticelListBinding
    private val data = ArrayList<ArticleEntity>()
    private val dataSearch = ArrayList<ArticleEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticelListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ArticleViewModel::class.java]


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(searchArticle: String?): Boolean {
                data.clear()
                val searchText = searchArticle!!
                if (searchText.isNotEmpty()){
                    dataSearch.forEach {
                        if (it.article_title.contains(searchText)){
                            data.add(it)
                        }
                    }
                    binding.recyclerView.adapter?.notifyDataSetChanged()

                } else {
                    data.clear()
                    data.addAll(dataSearch)
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                }
                return false
            }

            override fun onQueryTextChange(searchArticle: String?): Boolean {
                return false
            }
        })
        init()
        
        setToolbar()
    }

    private fun init() {
        viewModel.setRepository(ArticleRepository())
        viewModel.getAllArticles().observe(this){
            getData(it)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ArticleAdapter(binding.recyclerView)
        adapter.onLoadMoreListener = this
        binding.recyclerView.adapter = adapter
    }


    private fun setToolbar(){
        supportActionBar?.hide()
        binding.ibBack.setOnClickListener {
            finish()
        }
    }

    override fun onLoadMore() {
        Log.d("42069","HALOBANG")
        viewModel.loadNewArticles().observe(this){
            getData(it)
        }
    }

    private fun getData(it: ApiResponse<DataResponse<ArticleEntity>>){
        when(it) {
            is ApiResponse.Success -> {
                binding.loading.hide()
                val articles = it.data
                Log.d("42069", articles.data.count().toString())
                Log.d("42069", articles.meta.toString())
                viewModel.setPage(articles.meta)
                if (!articles.data.isNullOrEmpty()) {
                    data.clear()
                    data.addAll(articles.data.filterNotNull())
                    Log.d("42069", data.count().toString())
                    binding.tvEmpty.hide()
                    binding.recyclerView.show()
                    adapter.setLoad()
                    adapter.setData(data)
                } else {
                    binding.tvEmpty.show()
                    binding.recyclerView.hide()
                }
            }
            is ApiResponse.Error -> {
                val snackbar =
                    Snackbar.make(binding.parentArticleLayout, it.error, Snackbar.LENGTH_LONG)
                snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.error_red))
                snackbar.show()
                binding.loading.hide()
            }
            is ApiResponse.Loading -> {
                binding.loading.show()
            }
        }
    }
}