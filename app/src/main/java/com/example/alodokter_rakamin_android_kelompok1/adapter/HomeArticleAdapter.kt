package com.example.alodokter_rakamin_android_kelompok1.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.data.entity.ArticleEntity

class HomeArticleAdapter (private val data: ArrayList<ArticleEntity>):
    RecyclerView.Adapter<HomeArticleAdapter.HomeArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeArticleViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_home_article,
            parent, false)
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: HomeArticleViewHolder, position: Int) {
        holder.bind(data[position])
//        holder.view.setOnClickListener { listener.onClick( result ) }
    }

    fun setData(value: ArrayList<ArticleEntity>){
        data.addAll(value)
        notifyDataSetChanged()
    }

    inner class HomeArticleViewHolder(v: View):
        RecyclerView.ViewHolder(v) {
        private var img: ImageView? = null
        private var title: TextView? = null
        private var isi: TextView? = null

        init {
            img = v.findViewById(R.id.ivImageArticle)
            title = v.findViewById(R.id.tvDetailTitle)
            isi = v.findViewById(R.id.tvContentDesc)
        }

        fun bind(data: ArticleEntity){
            img?.setImageResource(R.drawable.ic_gambar_artikel1)
            title?.text = data.article_title
            Log.v("1422", data.toString())
            isi?.text = data.content_desc
        }
    }

}