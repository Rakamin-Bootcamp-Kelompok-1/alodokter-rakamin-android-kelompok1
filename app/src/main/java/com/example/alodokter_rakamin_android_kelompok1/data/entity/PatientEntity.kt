package com.example.alodokter_rakamin_android_kelompok1.data.entity

import com.google.gson.annotations.SerializedName

data class PatientEntity(
    @SerializedName("id") val id : Int,
    @SerializedName("patient_name") val patient_name : String,
    @SerializedName("status") val status : String,
    @SerializedName("gender") val gender : String,
    @SerializedName("birth_date") val birth_date : String,
    @SerializedName("age") val age : Int,
    @SerializedName("blood_type") val blood_type : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
)