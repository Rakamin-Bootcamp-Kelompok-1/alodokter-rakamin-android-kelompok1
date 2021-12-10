package com.example.alodokter_rakamin_android_kelompok1.data.entity

import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("id") val id : Int,
    @SerializedName("full_name") val full_name : String,
    @SerializedName("password_digest") val password_digest : String,
    @SerializedName("age") val age : Int,
    @SerializedName("email") val email : String,
    @SerializedName("gender") val gender : String,
    @SerializedName("birth_date") val birth_date : String,
    @SerializedName("phone_number") val phone_number : String,
    @SerializedName("image_path") val image_path : String,
    @SerializedName("is_admin") val is_admin : String,
    @SerializedName("is_active") val is_active : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
)