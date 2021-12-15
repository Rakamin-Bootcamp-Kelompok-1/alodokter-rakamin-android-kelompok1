package com.example.alodokter_rakamin_android_kelompok1.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.data.model.ArticleVertical

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

    fun bind(data: ArticleVertical){
        img?.setImageResource(data.img)
        Log.d("test",data.title.toString())
        title?.setText(data.title)
        isi?.setText(data.isi)
    }
}