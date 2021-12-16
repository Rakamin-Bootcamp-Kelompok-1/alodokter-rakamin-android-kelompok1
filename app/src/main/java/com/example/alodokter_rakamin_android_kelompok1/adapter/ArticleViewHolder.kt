package com.example.alodokter_rakamin_android_kelompok1.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.data.entity.ArticleEntity

class ArticleViewHolder(v: View):
    RecyclerView.ViewHolder(v) {
    private var img: ImageView? = null
    private var title: TextView? = null
    private var isi: TextView? = null

    init {
        img = v.findViewById(R.id.ibArtikel1)
        title = v.findViewById(R.id.tvJudulArtikel1)
        isi = v.findViewById(R.id.tvIsiShortArtikel1)
    }

    fun bind(data: ArticleEntity){
        img?.setImageResource(R.drawable.ic_gambar_artikel1)
        title?.setText(data.article_title)
        Log.v("1422", data.toString())
        isi?.setText(data.content_desc)
    }
}