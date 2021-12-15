package com.example.alodokter_rakamin_android_kelompok1.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.alodokter_rakamin_android_kelompok1.api.ApiClient
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.data.entity.BookingEntity
import com.example.alodokter_rakamin_android_kelompok1.data.response.DataResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryRepository {

    fun getHistoryBooking(page:Int,per_page:Int = 10,json: JSONObject): MutableLiveData<ApiResponse<DataResponse<BookingEntity>>> {
        val data = MutableLiveData<ApiResponse<DataResponse<BookingEntity>>>()
        data.value = ApiResponse.Loading
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val responseBodyCallApi = ApiClient().getApi().getHistoryBookings(page,per_page,requestBody)
        responseBodyCallApi.enqueue(object : Callback<DataResponse<BookingEntity>> {
            override fun onResponse(
                call: Call<DataResponse<BookingEntity>>,
                response: Response<DataResponse<BookingEntity>>
            ) {
                if(response.isSuccessful){
                    val booking = response.body()
                    if(booking != null) {
                        if (booking.articles.isEmpty()) data.value = ApiResponse.Error("Booking history not found")
                        else {
                            // jika ada local add all data ke room db
                            data.value = ApiResponse.Success(booking)
                        }
                    } else data.value = ApiResponse.Error("Booking history not found")
                } else data.value = ApiResponse.Error("Action is interrupted")
            }

            override fun onFailure(call: Call<DataResponse<BookingEntity>>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }

        })
        return data
    }
}