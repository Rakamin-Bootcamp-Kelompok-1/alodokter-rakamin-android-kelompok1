package com.example.alodokter_rakamin_android_kelompok1.data.entity

import com.google.gson.annotations.SerializedName

data class DoctorEntity(

    @SerializedName("id")
    val id: Int,
    @SerializedName("doctor_name")
    val doctor_name: String,
    @SerializedName("speciality")
    val speciality: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("star")
    val star: String,
    @SerializedName("location_practice")
    val location_practice: String,
    @SerializedName("biography")
    val biography: String,
    @SerializedName("education")
    val education: String,
    @SerializedName("price_rate")
    val price_rate: Int,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("updated_at")
    val updated_at: String,
    @SerializedName("image_path")
    val image_path: String?

)