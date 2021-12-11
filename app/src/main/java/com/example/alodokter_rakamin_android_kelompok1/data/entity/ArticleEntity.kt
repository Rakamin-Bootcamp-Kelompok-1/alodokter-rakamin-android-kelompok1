package com.example.alodokter_rakamin_android_kelompok1.data.entity

import com.google.gson.annotations.SerializedName

data class ArticleEntity(

    @SerializedName("id") val id : Int,
    @SerializedName("article_category") val article_category : String,
    @SerializedName("article_title") val article_title : String,
    @SerializedName("content_desc") val content_desc : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String

)
