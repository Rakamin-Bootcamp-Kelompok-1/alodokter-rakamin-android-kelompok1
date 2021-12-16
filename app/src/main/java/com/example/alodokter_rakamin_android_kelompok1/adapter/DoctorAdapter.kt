package com.example.alodokter_rakamin_android_kelompok1.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.data.entity.DoctorEntity

class DoctorAdapter (private val data: ArrayList<DoctorEntity>):
    RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : DoctorAdapter.DoctorViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_dokter, parent, false)
        return DoctorViewHolder(inflater)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setData(value: ArrayList<DoctorEntity>){
        data.addAll(value)
        notifyDataSetChanged()
    }

    inner class DoctorViewHolder(v: View): RecyclerView.ViewHolder(v){

        private var img: ImageView? = null
        private var name: TextView? = null
        private var location: TextView? = null
        private var specialist: TextView? = null
        private var star: TextView? = null

        init {
            img = v.findViewById(R.id.ivDoctor)
            name = v.findViewById(R.id.tvDoctorName)
            location = v.findViewById(R.id.tvLoc)
            star = v.findViewById(R.id.tvStar)
            specialist = v.findViewById(R.id.tvSpecialist)
        }

        fun bind(data: DoctorEntity){
            img?.setImageResource(R.drawable.shawn_doctor)
            name?.text = data.doctor_name
            Log.v("1422", data.toString())
            star?.text = data.star
            specialist?.text = data.speciality
            location?.text = data.location_practice
        }


    }
}