package com.example.alodokter_rakamin_android_kelompok1.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alodokter_rakamin_android_kelompok1.data.repository.ArticleRepository
import com.example.alodokter_rakamin_android_kelompok1.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private lateinit var repository: ArticleRepository
    private lateinit var userRepository: UserRepository

    fun getAllArticles() = repository.getArticles(1, 10)

    fun getUser(token: String) = userRepository.getUser(token)

    fun setRepository(articleRepository: ArticleRepository){
        repository = articleRepository
    }

    fun setUserRepository(userRepository: UserRepository){
        this.userRepository = userRepository
    }
}