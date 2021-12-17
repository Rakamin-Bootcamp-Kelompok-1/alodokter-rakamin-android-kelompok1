package com.example.alodokter_rakamin_android_kelompok1.view.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.adapter.RecentArticleAdapter
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.config.hide
import com.example.alodokter_rakamin_android_kelompok1.config.show
import com.example.alodokter_rakamin_android_kelompok1.data.entity.ArticleEntity
import com.example.alodokter_rakamin_android_kelompok1.data.repository.ArticleRepository
import com.example.alodokter_rakamin_android_kelompok1.databinding.ActivityDetailArtikelBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_artikel.*
import kotlinx.android.synthetic.main.activity_edit_profile.ibBack

class ArticleDetailActivity : AppCompatActivity(){

    companion object {
        const val TITLE = "title_article"
        const val CONTENT_DESC = "content_desc_article"
        const val IMAGE = "image_article"
    }

    private lateinit var binding: ActivityDetailArtikelBinding
    private lateinit var viewModel: ArticleViewModel
    private val data = ArrayList<ArticleEntity>()
    lateinit var adapter: RecentArticleAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ArticleViewModel::class.java]

        val title = intent.getStringExtra(TITLE)
        val content_desc = intent.getStringExtra(CONTENT_DESC)
        val image_article = intent.getStringExtra(IMAGE)

        binding.tvJudulArtikel.text = title
        binding.tvIsiArtikel.text = content_desc
        Glide.with(this)
            .load(image_article)
            .error(R.drawable.ic_gambar_artikel1)
            .into(ivArtikel)

        init()
        binding.rvRecentPost.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvRecentPost.adapter = adapter

        binding.btnSeeAll.setOnClickListener {
            finish()
        }

        supportActionBar?.hide()
        ibBack.setOnClickListener {
            finish()
        }
    }

    private fun init(){
        viewModel.setRepository(ArticleRepository())
        viewModel.getRecentArticle().observe(this){
            when(it){
                is ApiResponse.Success -> {
                    val articles = it.data
                    if (articles != null){
                        data.addAll(articles.data.filterNotNull())
                        binding.rvRecentPost.show()
                        adapter.setData(data)

                    } else {
                        binding.rvRecentPost.hide()
                    }

                }
                is ApiResponse.Error -> {
                    val snackbar = Snackbar.make(binding.parentDetailArticleLayout, it.error, Snackbar.LENGTH_LONG)
                    snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.error_red))
                    snackbar.show()
                }
            }
        }

        adapter = RecentArticleAdapter(data)
    }
}