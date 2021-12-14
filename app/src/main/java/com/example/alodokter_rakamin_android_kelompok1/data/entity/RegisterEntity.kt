package com.example.alodokter_rakamin_android_kelompok1.data.entity

import com.google.gson.annotations.SerializedName

data class RegisterEntity(
    @SerializedName("full_name") val full_name : String,
    @SerializedName("password") val password_digest : String,
    @SerializedName("email") val email : String,
    @SerializedName("age") val age : Int? = null,
    @SerializedName("gender") val gender : String,
    @SerializedName("birth_date") val birth_date : String,
    @SerializedName("phone_number") val phone_number : String?,
    @SerializedName("image_path") val image_path : String? = null,
    @SerializedName("is_admin") val is_admin : Boolean = false,
    @SerializedName("is_active") val is_active : Boolean = false

)