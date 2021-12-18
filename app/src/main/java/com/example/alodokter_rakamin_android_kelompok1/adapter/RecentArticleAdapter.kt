package com.example.alodokter_rakamin_android_kelompok1.adapter

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.data.entity.ArticleEntity
import com.example.alodokter_rakamin_android_kelompok1.view.article.ArticleDetailActivity

class RecentArticleAdapter:
    RecyclerView.Adapter<RecentArticleAdapter.RecentArticleViewHolder>() {

    private val data: ArrayList<ArticleEntity> = ArrayList()

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

    inner class RecentArticleViewHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener{


        private var img: ImageView? = null
        private var contentDesc :TextView? = null
        private lateinit var result: ArticleEntity

        init {
            img = v.findViewById(R.id.ivRecentPost)
            contentDesc = v.findViewById(R.id.tvRecentPost)
            v.setOnClickListener(this)
        }

        fun bind(data: ArticleEntity){
            if (data.image_data != null){
                val image = Uri.parse(data.image_data)
                Glide.with(itemView.context)
                    .load(image)
                    .error(R.drawable.ic_gambar_artikel2)
                    .into(img as ImageView)
            } else {
                img?.setImageResource(R.drawable.ic_gambar_artikel2)
            }
            contentDesc?.text = data.article_title
            result = data
        }

        override fun onClick(v: View?) {
            val intent = Intent(itemView.context, ArticleDetailActivity::class.java)
            intent.putExtra(ArticleDetailActivity.TITLE, result.article_title )
            intent.putExtra(ArticleDetailActivity.CONTENT_DESC, result.content_desc)
            intent.putExtra(ArticleDetailActivity.IMAGE, result.image_data)
            itemView.context.startActivity(intent)
            (itemView.context as Activity).finish()
        }

    }
}