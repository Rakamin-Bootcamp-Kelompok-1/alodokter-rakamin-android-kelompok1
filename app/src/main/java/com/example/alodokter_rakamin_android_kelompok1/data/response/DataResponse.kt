package com.example.alodokter_rakamin_android_kelompok1.data.response

import com.google.gson.annotations.SerializedName

data class DataResponse<T> (
    @SerializedName("data") val articles :List<T?>,
    @SerializedName("meta") val meta: MetaArticleResponse
)