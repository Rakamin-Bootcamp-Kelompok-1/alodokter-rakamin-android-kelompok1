package com.example.alodokter_rakamin_android_kelompok1.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.data.entity.ArticleEntity
import com.example.alodokter_rakamin_android_kelompok1.data.entity.DoctorEntity
import com.example.alodokter_rakamin_android_kelompok1.view.article.ArticleDetailActivity
import kotlinx.android.synthetic.main.item_specialist.view.*
import kotlin.collections.ArrayList

class SpecialistAdapter(var data: ArrayList<Specialist>) :
    RecyclerView.Adapter<SpecialistAdapter.SpecialistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :SpecialistViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_specialist, parent, false)
        return SpecialistViewHolder(inflater)
    }
    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: SpecialistViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class SpecialistViewHolder(v: View) :
        RecyclerView.ViewHolder(v) {
        private var img: ImageView? = null
        private var title: TextView? = null

        init {
            img = v.findViewById(R.id.ivSpecialistDoctor)
            title = v.findViewById(R.id.tvSpecialistDoctor)
        }

        fun bind(data: Specialist) {
            Glide.with(itemView.context)
                .load(data.img)
                .error(R.drawable.ic_dokter_umum)
                .into(img as ImageView)
            title?.text = data.title
        }
    }
}

