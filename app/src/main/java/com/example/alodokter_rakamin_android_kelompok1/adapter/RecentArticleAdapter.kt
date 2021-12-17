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
import com.example.alodokter_rakamin_android_kelompok1.data.entity.DoctorEntity

class RecentArticleAdapter (private val data: ArrayList<ArticleEntity>):
    RecyclerView.Adapter<RecentArticleAdapter.RecentArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecentArticleAdapter.RecentArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_recent_post, parent, false)
        return RecentArticleViewHolder(inflater)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecentArticleViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setData(value: ArrayList<ArticleEntity>){
        data.addAll(value)
        notifyDataSetChanged()
    }

    inner class RecentArticleViewHolder(v: View): RecyclerView.ViewHolder(v){

        private var img: ImageView? = null
        private var content_desc :TextView? = null

        init {
            img = v.findViewById(R.id.ivRecentPost)
            content_desc = v.findViewById(R.id.tvRecentPost)
        }

        fun bind(data: ArticleEntity){
            img?.setImageResource(R.drawable.ic_gambar_artikel2)
            content_desc?.text = data.content_desc
        }


    }
}