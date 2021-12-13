package com.example.alodokter_rakamin_android_kelompok1.data.model

data class Specialist (
    val result: ArrayList<Result>
) {
    data class Result (
        val title: String,
        val image: String)
}