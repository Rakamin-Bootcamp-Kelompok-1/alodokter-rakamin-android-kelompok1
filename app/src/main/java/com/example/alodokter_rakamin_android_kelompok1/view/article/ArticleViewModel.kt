package com.example.alodokter_rakamin_android_kelompok1.view.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.data.entity.ArticleEntity
import com.example.alodokter_rakamin_android_kelompok1.data.repository.ArticleRepository
import com.example.alodokter_rakamin_android_kelompok1.data.response.DataResponse
import com.example.alodokter_rakamin_android_kelompok1.data.response.MetaResponse

class ArticleViewModel : ViewModel() {
    private lateinit var repository: ArticleRepository
    private var page = 1
    private var totalPage = 1

    fun getAllArticles(page:Int = 1, perPage: Int = 10) = repository.getArticles(page, perPage)


    fun setRepository(articleRepository: ArticleRepository){
        repository = articleRepository
    }

    fun setPage(metaResponse: MetaResponse){
        totalPage = metaResponse.totalPage
    }

    fun loadNewArticles() : MutableLiveData<ApiResponse<DataResponse<ArticleEntity>>>{
        return if(totalPage > page){
            page++
            getAllArticles(page)
        } else {
            val data : MutableLiveData<ApiResponse<DataResponse<ArticleEntity>>> = MutableLiveData()
            data.value = ApiResponse.Error("Last Articles")
            data
        }
    }
}