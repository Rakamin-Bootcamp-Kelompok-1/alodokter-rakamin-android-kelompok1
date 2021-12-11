package com.example.alodokter_rakamin_android_kelompok1.data.entity

import com.google.gson.annotations.SerializedName

data class RegisterEntity(
    @SerializedName("full_name") val full_name : String,
    @SerializedName("password_digest") val password_digest : String,
    @SerializedName("email") val email : String,
    @SerializedName("gender") val gender : String,
    @SerializedName("birth_date") val birth_date : String,
    @SerializedName("phone_number") val phone_number : String,
)