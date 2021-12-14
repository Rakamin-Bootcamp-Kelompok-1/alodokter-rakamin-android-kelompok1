package com.example.alodokter_rakamin_android_kelompok1.data.model

data class HomeArticle (
    val result: ArrayList<Result>
) {
    data class Result (
        val article_category: String,
        val article_title: String,
        val image_url: String,
        val content_desc: String
    )
}