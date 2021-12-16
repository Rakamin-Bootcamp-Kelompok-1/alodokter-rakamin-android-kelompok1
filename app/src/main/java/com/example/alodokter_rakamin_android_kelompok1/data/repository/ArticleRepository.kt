package com.example.alodokter_rakamin_android_kelompok1.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.alodokter_rakamin_android_kelompok1.api.ApiClient
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.data.entity.ArticleEntity
import com.example.alodokter_rakamin_android_kelompok1.data.response.DataResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository {

    fun getArticles(page:Int,per_page:Int = 10): MutableLiveData<ApiResponse<DataResponse<ArticleEntity>>>{
        val data = MutableLiveData<ApiResponse<DataResponse<ArticleEntity>>>()
        data.value = ApiResponse.Loading
        val responseBodyCallApi = ApiClient().getApi().getArticles(page,per_page)
        responseBodyCallApi.enqueue(object : Callback<DataResponse<ArticleEntity>>{
            override fun onResponse(
                call: Call<DataResponse<ArticleEntity>>,
                response: Response<DataResponse<ArticleEntity>>
            ) {
                if(response.isSuccessful){
                    Log.v("1422", response.toString())
                    val articles = response.body()
                    if(articles != null) {
                        if (articles.data.isEmpty()) data.value = ApiResponse.Error("Articles not found")
                        else {
                            // jika ada local add all data ke room db
                            data.value = ApiResponse.Success(articles)
                        }
                    } else data.value = ApiResponse.Error("Articles not found")
                } else data.value = ApiResponse.Error("Action is interrupted")
            }

            override fun onFailure(call: Call<DataResponse<ArticleEntity>>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }
        })
        return data
    }

    fun getDetailArticle(id: Int): MutableLiveData<ApiResponse<ArticleEntity>>{
        val data = MutableLiveData<ApiResponse<ArticleEntity>>()
        data.value = ApiResponse.Loading
        val responseBodyCallApi = ApiClient().getApi().getDetailArticle(id)
        responseBodyCallApi.enqueue(object : Callback<ArticleEntity>{
            override fun onResponse(
                call: Call<ArticleEntity>,
                response: Response<ArticleEntity>
            ) {
                if(response.isSuccessful){
                    val article = response.body()
                    if(article != null) {
                        data.value = ApiResponse.Success(article)
                    } else data.value = ApiResponse.Error("Article not found")
                } else data.value = ApiResponse.Error("Action is interrupted")
            }

            override fun onFailure(call: Call<ArticleEntity>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }
        })
        return data
    }

    fun getArticlesByCategory(page:Int,per_page:Int = 10,category: String): MutableLiveData<ApiResponse<DataResponse<ArticleEntity>>>{
        val data = MutableLiveData<ApiResponse<DataResponse<ArticleEntity>>>()
        data.value = ApiResponse.Loading
        val json = JSONObject()
        json.put("article_category",category)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val responseBodyCallApi = ApiClient().getApi().getArticlesByCategory(page,per_page,requestBody)
        responseBodyCallApi.enqueue(object : Callback<DataResponse<ArticleEntity>>{
            override fun onResponse(
                call: Call<DataResponse<ArticleEntity>>,
                response: Response<DataResponse<ArticleEntity>>
            ) {
                if(response.isSuccessful){
                    Log.v("1422", response.toString())
                    val articles = response.body()
                    if(articles != null) {
                        if (articles.data.isEmpty()) data.value = ApiResponse.Error("Articles not found")
                        else {
                            // jika ada local add all data ke room db
                            data.value = ApiResponse.Success(articles)
                        }
                    } else data.value = ApiResponse.Error("Articles not found")
                } else data.value = ApiResponse.Error("Action is interrupted")
            }

            override fun onFailure(call: Call<DataResponse<ArticleEntity>>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }
        })
        return data
    }

    fun searchArticlesTitle(page:Int, per_page:Int = 10, title: String): MutableLiveData<ApiResponse<DataResponse<ArticleEntity>>>{
        val data = MutableLiveData<ApiResponse<DataResponse<ArticleEntity>>>()
        data.value = ApiResponse.Loading
        val json = JSONObject()
        json.put("article_title",title)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val responseBodyCallApi = ApiClient().getApi().searchArticleTitle(page,per_page,requestBody)
        responseBodyCallApi.enqueue(object : Callback<DataResponse<ArticleEntity>>{
            override fun onResponse(
                call: Call<DataResponse<ArticleEntity>>,
                response: Response<DataResponse<ArticleEntity>>
            ) {
                if(response.isSuccessful){
                    Log.v("1422", response.toString())
                    val articles = response.body()
                    if(articles != null) {
                        if (articles.data.isEmpty()) data.value = ApiResponse.Error("Articles not found")
                        else {
                            // jika ada local add all data ke room db
                            data.value = ApiResponse.Success(articles)
                        }
                    } else data.value = ApiResponse.Error("Articles not found")
                } else data.value = ApiResponse.Error("Action is interrupted")
            }

            override fun onFailure(call: Call<DataResponse<ArticleEntity>>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }
        })
        return data
    }
}