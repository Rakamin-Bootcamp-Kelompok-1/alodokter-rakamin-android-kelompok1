package com.example.alodokter_rakamin_android_kelompok1.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alodokter_rakamin_android_kelompok1.data.model.ArticleVertical
import com.example.alodokter_rakamin_android_kelompok1.R

class ArticleAdapter (private val data: ArrayList<ArticleVertical>): RecyclerView.Adapter<ArticleViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.rv_article_vertical, parent, false)
        return ArticleViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        Log.d("test",data.size.toString())
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}