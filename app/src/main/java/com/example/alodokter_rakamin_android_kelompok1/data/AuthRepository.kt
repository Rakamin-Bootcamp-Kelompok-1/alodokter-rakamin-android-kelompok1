package com.example.alodokter_rakamin_android_kelompok1.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.alodokter_rakamin_android_kelompok1.api.ApiClient
import com.example.alodokter_rakamin_android_kelompok1.data.entity.LoginEntity
import com.example.alodokter_rakamin_android_kelompok1.data.entity.RegisterEntity
import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

    fun getRegister(registerEntity: RegisterEntity): MutableLiveData<UserResponse> {
        val json = JSONObject()
        val mutableLiveData = MutableLiveData<UserResponse>()
        json.put("full_name", registerEntity.full_name)
        json.put("password", registerEntity.password_digest)
        json.put("age", registerEntity.age)
        json.put("email", registerEntity.email)
        json.put("gender", registerEntity.gender)
        json.put("birth_date", registerEntity.birth_date)
        json.put("phone_number", registerEntity.phone_number)
        json.put("image_path", registerEntity.image_path)
        json.put("is_admin", registerEntity.is_admin)
        json.put("is_active", registerEntity.is_active)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        Log.d("TEST1",json.toString())
        val responseBodyCallApi = ApiClient().getApi().register(requestBody)
        responseBodyCallApi.enqueue(object  : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    val data = response.body()
                    if(data != null) mutableLiveData.value = data
                    else mutableLiveData.value = UserResponse(error = "Null data")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                mutableLiveData.value = UserResponse(error = t.toString())
            }

        })
        return mutableLiveData
    }


    fun getLogin(loginEntity: LoginEntity): MutableLiveData<UserResponse> {
        val json = JSONObject()
        json.put("email", loginEntity.email)
        json.put("password", loginEntity.password_digest)
        val mutableLiveData = MutableLiveData<UserResponse>()

        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val responseBodyCallApi = ApiClient().getApi().login(requestBody)
        responseBodyCallApi.enqueue(object  : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("TEST",response.body().toString())
                val data = response.body()
                mutableLiveData.value = data!!
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                mutableLiveData.value = UserResponse(error = t.toString())
            }

        })
        return mutableLiveData
    }


}