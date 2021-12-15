package com.example.alodokter_rakamin_android_kelompok1.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.alodokter_rakamin_android_kelompok1.api.ApiClient
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.data.entity.ScheduleEntity
import com.example.alodokter_rakamin_android_kelompok1.data.response.DataResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleRepository {

    fun getSchedulesDoctor(page:Int,per_page:Int = 10,id: Int): MutableLiveData<ApiResponse<DataResponse<ScheduleEntity>>>{
        val data = MutableLiveData<ApiResponse<DataResponse<ScheduleEntity>>>()
        data.value = ApiResponse.Loading
        val json = JSONObject()
        json.put("doctor_id",id)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val responseBodyCallApi = ApiClient().getApi().getSchedulesDoctor(page,per_page,requestBody)
        responseBodyCallApi.enqueue(object : Callback<DataResponse<ScheduleEntity>> {
            override fun onResponse(
                call: Call<DataResponse<ScheduleEntity>>,
                response: Response<DataResponse<ScheduleEntity>>
            ) {
                if(response.isSuccessful){
                    val schedule = response.body()
                    if(schedule != null) {
                        if (schedule.articles.isEmpty()) data.value = ApiResponse.Error("Schedules not found")
                        else {
                            // jika ada local add all data ke room db
                            data.value = ApiResponse.Success(schedule)
                        }
                    } else data.value = ApiResponse.Error("Schedules not found")
                } else data.value = ApiResponse.Error("Action is interrupted")
            }

            override fun onFailure(call: Call<DataResponse<ScheduleEntity>>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }

        })
        return data
    }
}