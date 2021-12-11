package com.example.alodokter_rakamin_android_kelompok1.data.entity

import com.google.gson.annotations.SerializedName

data class LoginEntity(
    @SerializedName("email") val email : String,
    @SerializedName("password_digest") val password_digest : String,
)