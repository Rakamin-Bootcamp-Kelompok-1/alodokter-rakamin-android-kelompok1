package com.example.alodokter_rakamin_android_kelompok1.data.entity

import com.google.gson.annotations.SerializedName

data class BookingEntity (
    @SerializedName("id") val id: Int,
    @SerializedName("message") val message : Int,
    @SerializedName("payment_method") val paymentMethod : String,
    @SerializedName("total_price") val totalPrice : Int,
    @SerializedName("doctor_id") val doctorId : Int,
    @SerializedName("patient_id") val patientId : Int,
    @SerializedName("doctor_schedule_id") val doctorScheduleId : Int,
    @SerializedName("user_id") val userId : Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)