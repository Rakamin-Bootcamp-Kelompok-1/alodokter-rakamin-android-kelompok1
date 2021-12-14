package com.example.alodokter_rakamin_android_kelompok1.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.alodokter_rakamin_android_kelompok1.api.ApiClient
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.data.entity.PatientEntity
import com.example.alodokter_rakamin_android_kelompok1.data.response.DataResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PatientRepository {

    fun getPatientsByUser(page:Int,per_page:Int = 10,id: Int): MutableLiveData<ApiResponse<DataResponse<PatientEntity>>>{
        val data = MutableLiveData<ApiResponse<DataResponse<PatientEntity>>>()
        data.value = ApiResponse.Loading
        val json = JSONObject()
        json.put("user_id",id)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val responseBodyCallApi = ApiClient().getApi().getPatientsByUser(page,per_page,requestBody)
        responseBodyCallApi.enqueue(object : Callback<DataResponse<PatientEntity>>{
            override fun onResponse(
                call: Call<DataResponse<PatientEntity>>,
                response: Response<DataResponse<PatientEntity>>
            ) {
                if(response.isSuccessful){
                    val patients = response.body()
                    if(patients != null) {
                        if (patients.articles.isEmpty()) data.value = ApiResponse.Error("Patients not found")
                        else {
                            // jika ada local add all data ke room db
                            data.value = ApiResponse.Success(patients)
                        }
                    } else data.value = ApiResponse.Error("Patients not found")
                } else data.value = ApiResponse.Error("Action is interrupted")
            }

            override fun onFailure(call: Call<DataResponse<PatientEntity>>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }

        })
        return data
    }

    fun getDetailPatient(id: Int): MutableLiveData<ApiResponse<PatientEntity>>{
        val data = MutableLiveData<ApiResponse<PatientEntity>>()
        data.value = ApiResponse.Loading
        val responseBodyCallApi = ApiClient().getApi().getDetailPatient(id)
        responseBodyCallApi.enqueue(object :Callback<PatientEntity>{
            override fun onResponse(call: Call<PatientEntity>, response: Response<PatientEntity>) {
                if(response.isSuccessful){
                    val patient = response.body()
                    if(patient != null) {
                        data.value = ApiResponse.Success(patient)
                    } else data.value = ApiResponse.Error("Patient not found")
                } else data.value = ApiResponse.Error("Action is interrupted")
            }

            override fun onFailure(call: Call<PatientEntity>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }
        })
        return data
    }

    fun addDetailPatient(jsonObject: JSONObject): MutableLiveData<ApiResponse<PatientEntity>>{
        val data = MutableLiveData<ApiResponse<PatientEntity>>()
        data.value = ApiResponse.Loading
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val responseBodyCallApi = ApiClient().getApi().addPatient(requestBody)
        responseBodyCallApi.enqueue(object : Callback<PatientEntity>{
            override fun onResponse(call: Call<PatientEntity>, response: Response<PatientEntity>) {
                if(response.isSuccessful){
                    val patient = response.body()
                    if(patient != null){
                        data.value = ApiResponse.Success(patient)
                    } else data.value = ApiResponse.Error("Failed get patient")
                } else data.value = ApiResponse.Error("Action is interrupted. Retry submit")
            }

            override fun onFailure(call: Call<PatientEntity>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }
        })
        return data
    }
}