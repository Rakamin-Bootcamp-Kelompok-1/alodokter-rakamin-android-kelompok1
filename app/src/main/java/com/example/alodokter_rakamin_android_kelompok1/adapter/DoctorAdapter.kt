package com.example.alodokter_rakamin_android_kelompok1.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.data.entity.DoctorEntity

class DoctorAdapter (recyclerView: RecyclerView):
    RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    private var loading: Boolean = false
    lateinit var onLoadMoreListener: OnLoadMoreListener
    private var data: ArrayList<DoctorEntity> = ArrayList()

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
            if (data.image_path != null){
                val image = Uri.parse(data.image_path)
                Log.d("42069",image.toString())
                var requestOptions = RequestOptions()
                requestOptions = requestOptions.transform(RoundedCorners(50))
                Glide.with(itemView.context)
                    .load(image)
                    .fitCenter()
                    .apply(requestOptions)
                    .error(R.drawable.shawn_doctor)
                    .into(img as ImageView)
            } else {
                img?.setImageResource(R.drawable.shawn_doctor)
            }
            name?.text = data.doctor_name
            star?.text = data.star
            specialist?.text = data.speciality
            location?.text = data.location_practice
        }


    }
}