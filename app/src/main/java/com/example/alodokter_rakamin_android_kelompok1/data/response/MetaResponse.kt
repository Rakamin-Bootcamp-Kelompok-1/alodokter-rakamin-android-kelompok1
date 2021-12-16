package com.example.alodokter_rakamin_android_kelompok1.data.response

import com.google.gson.annotations.SerializedName

data class MetaResponse (
    @SerializedName("page") val page: Int,
    @SerializedName("next_page") val nextPage: Int,
    @SerializedName("prev_page") val prevPage: Int,
    @SerializedName("total_page") val totalPage: Int
)