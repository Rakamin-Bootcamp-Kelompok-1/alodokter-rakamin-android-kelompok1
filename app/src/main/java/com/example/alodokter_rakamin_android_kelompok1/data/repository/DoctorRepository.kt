package com.example.alodokter_rakamin_android_kelompok1.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.alodokter_rakamin_android_kelompok1.api.ApiClient
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.data.entity.DoctorEntity
import com.example.alodokter_rakamin_android_kelompok1.data.response.DataResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorRepository {

    fun getDoctors(page:Int,per_page:Int = 10): MutableLiveData<ApiResponse<DataResponse<DoctorEntity>>>{
        val data = MutableLiveData<ApiResponse<DataResponse<DoctorEntity>>>()
        data.value = ApiResponse.Loading
        val responseBodyCallApi = ApiClient().getApi().getDoctors(page,per_page)
        responseBodyCallApi.enqueue(object : Callback<DataResponse<DoctorEntity>>{
            override fun onResponse(
                call: Call<DataResponse<DoctorEntity>>,
                response: Response<DataResponse<DoctorEntity>>
            ) {
                if(response.isSuccessful){
                    val doctors = response.body()
                    if(doctors != null) {
                        if (doctors.data.isEmpty()) data.value = ApiResponse.Error("Doctors not found")
                        else {
                            // jika ada local add all data ke room db
                            data.value = ApiResponse.Success(doctors)
                        }
                    } else data.value = ApiResponse.Error("Doctors not found")
                } else data.value = ApiResponse.Error("Action is interrupted")
            }

            override fun onFailure(call: Call<DataResponse<DoctorEntity>>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }
        })

        return data
    }

    fun getDetailDoctor(id: Int): MutableLiveData<ApiResponse<DoctorEntity>>{
        val data = MutableLiveData<ApiResponse<DoctorEntity>>()
        data.value = ApiResponse.Loading
        val responseBodyCallApi = ApiClient().getApi().getDetailDoctor(id)
        responseBodyCallApi.enqueue(object :Callback<DoctorEntity>{
            override fun onResponse(call: Call<DoctorEntity>, response: Response<DoctorEntity>) {
                if(response.isSuccessful){
                    val doctor = response.body()
                    if(doctor != null) {
                        data.value = ApiResponse.Success(doctor)
                    } else data.value = ApiResponse.Error("Doctor not found")
                } else data.value = ApiResponse.Error("Action is interrupted")
            }

            override fun onFailure(call: Call<DoctorEntity>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }
        })
        return data
    }

    fun searchDoctorsTitle(page:Int,per_page:Int = 10,title: String): MutableLiveData<ApiResponse<DataResponse<DoctorEntity>>>{
        val data = MutableLiveData<ApiResponse<DataResponse<DoctorEntity>>>()
        data.value = ApiResponse.Loading
        val json = JSONObject()
        json.put("doctor_name",title)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val responseBodyCallApi = ApiClient().getApi().searchDoctorsTitle(page,per_page,requestBody)
        responseBodyCallApi.enqueue(object : Callback<DataResponse<DoctorEntity>>{
            override fun onResponse(
                call: Call<DataResponse<DoctorEntity>>,
                response: Response<DataResponse<DoctorEntity>>
            ) {
                if(response.isSuccessful){
                    val doctors = response.body()
                    if(doctors != null) {
                        if (doctors.data.isEmpty()) data.value = ApiResponse.Error("Doctors not found")
                        else {
                            // jika ada local add all data ke room db
                            data.value = ApiResponse.Success(doctors)
                        }
                    } else data.value = ApiResponse.Error("Doctors not found")
                } else data.value = ApiResponse.Error("Action is interrupted")
            }

            override fun onFailure(call: Call<DataResponse<DoctorEntity>>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }
        })

        return data
    }

    fun searchDoctorsBySpeciality(page:Int,per_page:Int = 10,category: String): MutableLiveData<ApiResponse<DataResponse<DoctorEntity>>>{
        val data = MutableLiveData<ApiResponse<DataResponse<DoctorEntity>>>()
        data.value = ApiResponse.Loading
        val json = JSONObject()
        json.put("speciality",category)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val responseBodyCallApi = ApiClient().getApi().getDoctorsByCategory(page,per_page,requestBody)
        responseBodyCallApi.enqueue(object : Callback<DataResponse<DoctorEntity>>{
            override fun onResponse(
                call: Call<DataResponse<DoctorEntity>>,
                response: Response<DataResponse<DoctorEntity>>
            ) {
                if(response.isSuccessful){
                    Log.v("1512", response.body().toString())
                    val doctors = response.body()
                    if(doctors != null) {
                        if (doctors.data.isEmpty()) data.value = ApiResponse.Error("Doctors not found")
                        else {
                            // jika ada local add all data ke room db
                            data.value = ApiResponse.Success(doctors)
                        }
                    } else data.value = ApiResponse.Error("Doctors not found")
                } else data.value = ApiResponse.Error("Action is interrupted")
            }

            override fun onFailure(call: Call<DataResponse<DoctorEntity>>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }
        })

        return data
    }
}