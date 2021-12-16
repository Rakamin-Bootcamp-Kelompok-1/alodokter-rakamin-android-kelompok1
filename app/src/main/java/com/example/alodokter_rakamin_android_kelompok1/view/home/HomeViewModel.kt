package com.example.alodokter_rakamin_android_kelompok1.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alodokter_rakamin_android_kelompok1.data.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private lateinit var repository: ArticleRepository


    fun getAllArticles() = repository.getArticles(1, 5)

    fun setRepository(articleRepository: ArticleRepository){
        repository = articleRepository
    }
}