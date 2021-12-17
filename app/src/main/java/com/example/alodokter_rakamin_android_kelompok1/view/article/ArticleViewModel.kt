package com.example.alodokter_rakamin_android_kelompok1.view.article

import androidx.lifecycle.ViewModel
import com.example.alodokter_rakamin_android_kelompok1.data.repository.ArticleRepository

class ArticleViewModel : ViewModel() {
    private lateinit var repository: ArticleRepository


    fun getAllArticles() = repository.getArticles(1, 60)

    fun setRepository(articleRepository: ArticleRepository){
        repository = articleRepository
    }



    fun getRecentArticle() = repository.getArticles(1, 3)

}