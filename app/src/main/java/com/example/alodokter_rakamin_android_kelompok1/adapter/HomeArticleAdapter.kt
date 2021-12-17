package com.example.alodokter_rakamin_android_kelompok1.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
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
        RecyclerView.ViewHolder(v), View.OnClickListener {
        private var img: ImageView? = null
        private var title: TextView? = null
        private var isi: TextView? = null
        private lateinit var result: ArticleEntity

        init {
            img = v.findViewById(R.id.ivImageArticle)
            title = v.findViewById(R.id.tvDetailTitle)
            isi = v.findViewById(R.id.tvContentDesc)
        }

        fun bind(data: ArticleEntity){
            if (data.image_data != null){
                val image = Uri.parse(data.image_data)
                Glide.with(itemView.context)
                    .load(image)
                    .error(R.drawable.ic_gambar_artikel1)
                    .into(img as ImageView)
            } else {
                img?.setImageResource(R.drawable.ic_gambar_artikel1)
            }
            title?.setText(data.article_title)
            Log.v("1422", data.toString())
            isi?.setText(data.content_desc)
            result = data
        }

        override fun onClick(view: View?) {
            val intent = Intent(itemView.context, ArticleDetailActivity::class.java)
            intent.putExtra(ArticleDetailActivity.TITLE, result.article_title )
            intent.putExtra(ArticleDetailActivity.CONTENT_DESC, result.content_desc)
            intent.putExtra(ArticleDetailActivity.IMAGE, result.image_data)
            itemView.context.startActivity(intent)
        }
    }

}