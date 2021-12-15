package com.example.alodokter_rakamin_android_kelompok1.data.model

data class Patient (
    val result: ArrayList<Result>
) {
    data class Result (
        val patient_name: String,
        val status: String,
        val gender: String,
        val birth_date: String,
        val age: Int,
        val blood_type: String,
        val user_id: Int
    )
}