package com.example.alodokter_rakamin_android_kelompok1.data.response

import com.example.alodokter_rakamin_android_kelompok1.data.entity.UserEntity
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("user") val user : UserEntity? = null,
    @SerializedName("token") val token : String? = null,
    @SerializedName("error") val error : String? = null
)