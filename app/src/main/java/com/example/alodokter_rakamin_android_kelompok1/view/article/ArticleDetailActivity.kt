package com.example.alodokter_rakamin_android_kelompok1.view.article

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alodokter_rakamin_android_kelompok1.R
import kotlinx.android.synthetic.main.activity_edit_profile.*

class ArticleDetailActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_artikel)

        supportActionBar?.hide()
        ibBack.setOnClickListener {
            Intent (this, ArticleListActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}