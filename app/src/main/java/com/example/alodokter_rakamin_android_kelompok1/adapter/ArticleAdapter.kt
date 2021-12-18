package com.example.alodokter_rakamin_android_kelompok1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.data.entity.ArticleEntity

class ArticleAdapter (recyclerView: RecyclerView): RecyclerView.Adapter<ArticleViewHolder>(){

    private var loading: Boolean = false
    lateinit var onLoadMoreListener: OnLoadMoreListener
    private var data: ArrayList<ArticleEntity> = ArrayList()

    init{
        if(recyclerView.layoutManager is LinearLayoutManager){
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = linearLayoutManager.itemCount
                    val lastVisible = linearLayoutManager.findLastVisibleItemPosition()
                    if(!loading && totalItemCount - 1 <= lastVisible && lastVisible > data.size - 2){
                        onLoadMoreListener.onLoadMore()
                        loading = true
                    }
                }
            })
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.rv_article_vertical, parent, false)
        return ArticleViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(value: ArrayList<ArticleEntity>){
        data.addAll(value)
        notifyDataSetChanged()
    }

    fun resetData(){
        data = ArrayList()
        notifyDataSetChanged()
    }

    fun setLoad(){
        loading = false
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }
}