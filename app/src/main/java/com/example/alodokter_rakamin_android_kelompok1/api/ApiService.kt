package com.example.alodokter_rakamin_android_kelompok1.api

import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    fun register(
        @Body requestBody: RequestBody
    ): Call<ResponseBody>

    @POST("login")
    fun login(
        @Body requestBody: RequestBody
    ): Call<UserResponse>

    @Headers(
        "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxfQ.oT7kSePnYs7eVIsRIzIi0UEC7XBclsrO3qrnXwic8Zg",
    )
    @POST("get-user")
    fun getUser(@Body requestBody: RequestBody): Call<ResponseBody>

    @Headers(
        "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxfQ.oT7kSePnYs7eVIsRIzIi0UEC7XBclsrO3qrnXwic8Zg",
    )
    @POST("get-doctor")
    fun getDoctor(@Body requestBody: RequestBody): Call<ResponseBody>

    @Headers(
        "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxfQ.oT7kSePnYs7eVIsRIzIi0UEC7XBclsrO3qrnXwic8Zg",
    )
    @POST("get-article")
    fun getArticle(@Body requestBody: RequestBody): Call<ResponseBody>

}