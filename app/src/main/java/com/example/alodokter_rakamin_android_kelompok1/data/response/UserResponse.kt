package com.example.alodokter_rakamin_android_kelompok1.data.response

import com.example.alodokter_rakamin_android_kelompok1.data.entity.UserEntity

data class UserResponse(
    val user : UserEntity? = null,
    val token : String? = null,
    val error : String? = null
)