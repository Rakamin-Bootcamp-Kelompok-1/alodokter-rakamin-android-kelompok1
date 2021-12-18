package com.example.alodokter_rakamin_android_kelompok1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alodokter_rakamin_android_kelompok1.R
import kotlin.collections.ArrayList

class SpecialistAdapter(var data: ArrayList<Specialist>) :
    RecyclerView.Adapter<SpecialistAdapter.SpecialistViewHolder>() {

    private var category = ""
    lateinit var onClickListener: onClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):SpecialistViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_specialist, parent, false)
        return SpecialistViewHolder(inflater)
    }
    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: SpecialistViewHolder, position: Int) {
        holder.bind(data[position])
    }

    interface onClick {
        fun onClick (category: String)
    }


    inner class SpecialistViewHolder(v: View) :
        RecyclerView.ViewHolder(v), View.OnClickListener {
        private var img: ImageView? = null
        private var title: TextView? = null

        init {
            img = v.findViewById(R.id.ivSpecialistDoctor)
            title = v.findViewById(R.id.tvSpecialistDoctor)
            v.setOnClickListener(this)
        }

        fun bind(data: Specialist) {
            Glide.with(itemView.context)
                .load(data.img)
                .error(R.drawable.ic_dokter_umum)
                .into(img as ImageView)
            title?.text = data.title
            category = data.title
        }

        override fun onClick(p0: View?) {
            onClickListener.onClick(category)
        }
    }
}

