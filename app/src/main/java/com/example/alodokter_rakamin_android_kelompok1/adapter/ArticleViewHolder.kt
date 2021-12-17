package com.example.alodokter_rakamin_android_kelompok1.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.data.entity.ArticleEntity
import com.example.alodokter_rakamin_android_kelompok1.view.article.ArticleDetailActivity
import kotlinx.android.synthetic.main.rv_article_vertical.view.*

class ArticleViewHolder(v: View):
    RecyclerView.ViewHolder(v), View.OnClickListener{


    private var img: ImageView? = null
    private var title: TextView? = null
    private var isi: TextView? = null
    private lateinit var result: ArticleEntity

    init {
        img = v.findViewById(R.id.ivArtikel)
        title = v.findViewById(R.id.tvJudulArtikel)
        isi = v.findViewById(R.id.tvIsiShortArtikel)
        v.setOnClickListener(this)
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