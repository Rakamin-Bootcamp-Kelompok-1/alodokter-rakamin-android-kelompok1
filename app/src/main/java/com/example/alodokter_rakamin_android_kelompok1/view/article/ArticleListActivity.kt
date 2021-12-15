package com.example.alodokter_rakamin_android_kelompok1.view.article

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alodokter_rakamin_android_kelompok1.adapter.ArticleAdapter
import com.example.alodokter_rakamin_android_kelompok1.data.model.ArticleVertical
import com.example.alodokter_rakamin_android_kelompok1.R

class ArticleListActivity : AppCompatActivity(){

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ArticleAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articel_list)

        init()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        Log.d("test",recyclerView.size.toString())

    }


    private fun init() {
        recyclerView = findViewById(R.id.recycler_view)

        var data = ArrayList<ArticleVertical>()
        data.add(ArticleVertical(R.drawable.ic_gambar_artikel1,R.string.judul_artikel1,R.string.isi_short_artikel1))
        data.add(ArticleVertical(R.drawable.ic_gambar_artikel2,R.string.judul_artikel2,R.string.isi_short_artikel2))
        data.add(ArticleVertical(R.drawable.ic_gambar_artikel3,R.string.judul_artikel3,R.string.isi_short_artikel3))

        adapter = ArticleAdapter(data)
    }


}