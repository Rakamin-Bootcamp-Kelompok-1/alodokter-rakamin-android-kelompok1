package com.example.alodokter_rakamin_android_kelompok1.api

import com.example.alodokter_rakamin_android_kelompok1.data.entity.*
import com.example.alodokter_rakamin_android_kelompok1.data.response.DataResponse
import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //user
    @Headers(
        "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxfQ.oT7kSePnYs7eVIsRIzIi0UEC7XBclsrO3qrnXwic8Zg",
    )
    @PATCH("user/update/{id}")
    fun editUser(
        @Path("id") id: Int,
        @Body requestBody: RequestBody
    ) : Call<UserEntity>

    @POST("user/add")
    fun register(
        @Body requestBody: RequestBody
    ): Call<UserResponse>

    @GET("token_authenticate")
    fun getUser(@Header("Authorization") token: String): Call<UserEntity>

    @Headers(
        "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxfQ.oT7kSePnYs7eVIsRIzIi0UEC7XBclsrO3qrnXwic8Zg",
    )
    @POST("login")
    fun login(
        @Body requestBody: RequestBody
    ): Call<UserResponse>

    @POST("password/forgot")
    fun forgotPassword(
        @Body requestBody: RequestBody
    ): Call<ResponseBody>

    //article
    @Headers(
        "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxfQ.oT7kSePnYs7eVIsRIzIi0UEC7XBclsrO3qrnXwic8Zg",
    )
    @GET("articles")
    fun getArticles(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<DataResponse<ArticleEntity>>

    @GET("articles/category")
    fun getArticlesByCategory(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<DataResponse<ArticleEntity>>//diganti

    @GET("articles/{id}")
    fun getDetailArticle(
        @Path("id") id: Int
    ) : Call<ArticleEntity>

    //doctor
    @Headers(
        "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxfQ.oT7kSePnYs7eVIsRIzIi0UEC7XBclsrO3qrnXwic8Zg",
    )
    @GET("doctors")
    fun getDoctors(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<DataResponse<DoctorEntity>>

    @Headers(
        "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxfQ.oT7kSePnYs7eVIsRIzIi0UEC7XBclsrO3qrnXwic8Zg",
    )
    @GET("doctor/{id}")
    fun getDetailDoctor(
        @Path("id") id: Int
    ): Call<DoctorEntity>

    //patient
    @GET("patients/list")
    fun getPatientsByUser(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Body requestBody: RequestBody
    ): Call<DataResponse<PatientEntity>>//diganti

    @GET("patient/{id}")
    fun getDetailPatient(
        @Path("id") id: Int
    ): Call<PatientEntity>

    @Headers(
        "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxfQ.oT7kSePnYs7eVIsRIzIi0UEC7XBclsrO3qrnXwic8Zg",
    )
    @POST("patients")
    fun addPatient(
        @Body requestBody: RequestBody
    ): Call<PatientEntity>

    @Headers(
        "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxfQ.oT7kSePnYs7eVIsRIzIi0UEC7XBclsrO3qrnXwic8Zg",
    )
    @PATCH("patient/{id}")
    fun editPatient(
        @Path("id") id: Int,
        @Body requestBody: RequestBody
    ) : Call<PatientEntity>

    @DELETE("patient/{id}")
    fun deletePatient(
        @Path("id") id: Int,
    ): Call<ResponseBody>

    //schedule
    @GET("schedule/doctor")
    fun getSchedulesDoctor(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Body requestBody: RequestBody
    ): Call<DataResponse<ScheduleEntity>>//diganti

    //booking
    @GET("bookings")
    fun getBookings(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Body requestBody: RequestBody
    ): Call<DataResponse<BookingEntity>>//diganti

    @Headers(
        "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxfQ.oT7kSePnYs7eVIsRIzIi0UEC7XBclsrO3qrnXwic8Zg",
    )
    @POST("bookings")
    fun addBooking(
        @Body requestBody: RequestBody
    ): Call<BookingEntity>

    @GET("bookings/history")
    fun getHistoryBookings(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Body requestBody: RequestBody
    ): Call<DataResponse<BookingEntity>>//diganti

    @DELETE("bookings/{id}")
    fun deleteBooking(
        @Path("id") id: Int,
    ): Call<ResponseBody>


}