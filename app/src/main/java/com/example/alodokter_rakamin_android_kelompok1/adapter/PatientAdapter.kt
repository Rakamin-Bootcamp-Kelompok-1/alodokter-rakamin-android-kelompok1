package com.example.alodokter_rakamin_android_kelompok1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.data.entity.PatientEntity

class PatientAdapter (var results: ArrayList<PatientEntity>):
    RecyclerView.Adapter<PatientAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_patient,
            parent, false)
    )

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
//        holder.view.setOnClickListener { listener.onClick( result ) }
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(data: List<PatientEntity>){
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }

}