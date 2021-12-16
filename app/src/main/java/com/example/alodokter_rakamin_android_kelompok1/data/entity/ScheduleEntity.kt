package com.example.alodokter_rakamin_android_kelompok1.data.entity

import com.google.gson.annotations.SerializedName

data class ScheduleEntity (
    @SerializedName("id") val id: Int,
    @SerializedName("doctor_id") val doctorId : Int,
    @SerializedName("day") val day : String,
    @SerializedName("date") val date : String,
    @SerializedName("month") val month : String,
    @SerializedName("year") val year : String,
    @SerializedName("time_practice") val timePractice : String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)