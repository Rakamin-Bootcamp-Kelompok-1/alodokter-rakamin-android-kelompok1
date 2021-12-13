package com.example.alodokter_rakamin_android_kelompok1.data.model

data class Doctor (
    val result: ArrayList<Result>
) {
    data class Result (
        val name: String,
        val image: String,
        val speciality: String,
        val location: String,
        val star: Int,
        val status: String,
        val education: String,
        val biography: String,
        val price_rate: Int
        )
}