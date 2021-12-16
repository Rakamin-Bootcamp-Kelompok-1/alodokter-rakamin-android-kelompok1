package com.example.alodokter_rakamin_android_kelompok1.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.alodokter_rakamin_android_kelompok1.api.ApiClient
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.example.alodokter_rakamin_android_kelompok1.data.entity.UserEntity
import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    fun editUser(context: Context, id: Int, user: JSONObject) : MutableLiveData<ApiResponse<UserResponse>>{
        val data = MutableLiveData<ApiResponse<UserResponse>>()
        data.value = ApiResponse.Loading
        val requestBody = user.toString().toRequestBody("application/json".toMediaTypeOrNull())
        Log.d("TEST1",user.toString())
        val responseBodyCallApi = ApiClient().getApi().editUser(id, requestBody)
        responseBodyCallApi.enqueue(object : Callback<UserEntity>{
            override fun onResponse(call: Call<UserEntity>, response: Response<UserEntity>) {
                val value = response.body()
                val token = SharedPreferences(context).getUserToken()
                data.value = ApiResponse.Success(UserResponse(value,token))
            }

            override fun onFailure(call: Call<UserEntity>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }
        })

        return data
    }

    fun forgotPassword(email: String) : MutableLiveData<ApiResponse<JSONObject>>{
        val data = MutableLiveData<ApiResponse<JSONObject>>()
        data.value = ApiResponse.Loading
        val json = JSONObject()
        json.put("email",email)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val responseBodyCallApi = ApiClient().getApi().forgotPassword(requestBody)
        responseBodyCallApi.enqueue(object: Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val value = response.body()
                if(value != null){
                    val jsonObject = JSONObject(value.string())
                    data.value = ApiResponse.Success(jsonObject)
                } else {
                    data.value = ApiResponse.Error("Invalid email")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                data.value = ApiResponse.Error(t.message as String)
            }
        })

        return data
    }

    fun getUser(token: String) : MutableLiveData<ApiResponse<UserResponse>>{
        val data = MutableLiveData<ApiResponse<UserResponse>>()
        data.value = ApiResponse.Loading
        val responseBodyCallApi = ApiClient().getApi().getUser(token)
        responseBodyCallApi.enqueue(object : Callback<UserEntity>{
            override fun onResponse(call: Call<UserEntity>, response: Response<UserEntity>) {
                val value = response.body()
                data.value = ApiResponse.Success(UserResponse(value,token))
            }

            override fun onFailure(call: Call<UserEntity>, t: Throwable) {
                data.value = ApiResponse.Error(t.message.toString())
            }
        })

        return data
    }
}