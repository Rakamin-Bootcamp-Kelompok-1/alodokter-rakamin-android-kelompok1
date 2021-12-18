package com.example.alodokter_rakamin_android_kelompok1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.data.entity.CalendarEntity

class ConfirmBookingAdapter(private val listener: (calendarEntity: CalendarEntity, position: Int) -> Unit) :
    RecyclerView.Adapter<ConfirmBookingAdapter.MyViewHolder>() {
    private val list = ArrayList<CalendarEntity>()

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(calendarEntity: CalendarEntity) {
            val calendarDay = itemView.findViewById<TextView>(R.id.tvDay)
            val calendarDate = itemView.findViewById<TextView>(R.id.tvTanggal)
            val cardView = itemView.findViewById<CardView>(R.id.cvTanggal)

            if (calendarEntity.isSelected) {
                calendarDay.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
                calendarDate.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.main_blue
                    )
                )
            } else {
                calendarDay.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.gray
                    )
                )
                calendarDate.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.gray
                    )
                )
                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
            }

            calendarDay.text = calendarEntity.calendarDay
            calendarDate.text = calendarEntity.calendarDate
            cardView.setOnClickListener {
                listener.invoke(calendarEntity, adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_waktu, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(calendarList: ArrayList<CalendarEntity>) {
        list.clear()
        list.addAll(calendarList)
        notifyDataSetChanged()
    }
}