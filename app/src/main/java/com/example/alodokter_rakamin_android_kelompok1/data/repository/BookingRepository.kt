package com.example.alodokter_rakamin_android_kelompok1.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.alodokter_rakamin_android_kelompok1.api.ApiClient
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.data.entity.BookingEntity
import com.example.alodokter_rakamin_android_kelompok1.data.response.DataResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingRepository {

    fun getBookings(page:Int,per_page:Int = 10,json: JSONObject): MutableLiveData<ApiResponse<DataResponse<BookingEntity>>>{
        val data = MutableLiveData<ApiResponse<DataResponse<BookingEntity>>>()
        data.value = ApiResponse.Loading
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val responseBodyCallApi = ApiClient().getApi().getBookings(page,per_page,requestBody)
        responseBodyCallApi.enqueue(object : Callback<DataResponse<BookingEntity>> {
            override fun onResponse(
                call: Call<DataResponse<BookingEntity>>,
                response: Response<DataResponse<BookingEntity>>
            ) {
                if(response.isSuccessful){
                    val booking = response.body()
                    if(booking != null) {
                        if (booking.data.isEmpty()) data.value = ApiResponse.Error("Booking not found")
                        else {
                            // jika ada local add all data ke room db
                            data.value = ApiResponse.Success(booking)
                        }
                    } else data.value = ApiResponse.Error("Booking not found")
                } else data.value = ApiResponse.Error("Action is interrupted")
            }

            override fun onFailure(call: Call<DataResponse<BookingEntity>>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }

        })
        return data
    }

    fun addBooking(jsonObject: JSONObject): MutableLiveData<ApiResponse<BookingEntity>>{
        val data = MutableLiveData<ApiResponse<BookingEntity>>()
        data.value = ApiResponse.Loading
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val responseBodyCallApi = ApiClient().getApi().addBooking(requestBody)
        responseBodyCallApi.enqueue(object : Callback<BookingEntity>{
            override fun onResponse(call: Call<BookingEntity>, response: Response<BookingEntity>) {
                if(response.isSuccessful){
                    val patient = response.body()
                    if(patient != null){
                        data.value = ApiResponse.Success(patient)
                    } else data.value = ApiResponse.Error("Failed get booking")
                } else data.value = ApiResponse.Error("Action is interrupted. Retry submit")
            }

            override fun onFailure(call: Call<BookingEntity>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }
        })
        return data
    }

    fun deleteBooking(id: Int) : MutableLiveData<ApiResponse<JSONObject>>{
        val data = MutableLiveData<ApiResponse<JSONObject>>()
        data.value = ApiResponse.Loading
        val responseBodyCallApi = ApiClient().getApi().deleteBooking(id)
        responseBodyCallApi.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    val value = response.body()
                    if(value != null){
                        val jsonObject = JSONObject(value.string())
                        data.value = ApiResponse.Success(jsonObject)
                    } else {
                        data.value = ApiResponse.Error("Booking has been deleted, but cannot get status")
                    }
                } else data.value = ApiResponse.Error("Can't delete booking")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }
        })
        return data
    }
}