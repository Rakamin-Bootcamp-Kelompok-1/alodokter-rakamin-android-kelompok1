package com.example.alodokter_rakamin_android_kelompok1.view.article

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.databinding.ActivityDetailArtikelBinding
import kotlinx.android.synthetic.main.activity_detail_artikel.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.ibBack

class ArticleDetailActivity : AppCompatActivity(){

    companion object {
        const val TITLE = "title_article"
        const val CONTENT_DESC = "content_desc_article"
        const val IMAGE = "image_article"
    }

    private lateinit var binding: ActivityDetailArtikelBinding
    private lateinit var viewModel: ArticleViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val title = intent.getStringExtra(TITLE)
        val content_desc = intent.getStringExtra(CONTENT_DESC)
        val image_article = intent.getStringExtra(IMAGE)

        binding.tvJudulArtikel.text = title
        binding.tvIsiArtikel.text = content_desc
        Glide.with(this)
            .load(image_article)
            .error(R.drawable.ic_gambar_artikel1)
            .into(ivArtikel)

        supportActionBar?.hide()
        ibBack.setOnClickListener {
            finish()
        }
    }
}