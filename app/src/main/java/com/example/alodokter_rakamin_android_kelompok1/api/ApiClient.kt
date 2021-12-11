package com.example.alodokter_rakamin_android_kelompok1.api

import com.example.alodokter_rakamin_android_kelompok1.config.Config
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {


    private fun getApi(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }


}