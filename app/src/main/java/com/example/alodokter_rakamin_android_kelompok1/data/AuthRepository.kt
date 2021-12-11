package com.example.alodokter_rakamin_android_kelompok1.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.alodokter_rakamin_android_kelompok1.api.ApiClient
import com.example.alodokter_rakamin_android_kelompok1.data.entity.LoginEntity
import com.example.alodokter_rakamin_android_kelompok1.data.entity.RegisterEntity
import com.example.alodokter_rakamin_android_kelompok1.data.response.UserResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AuthRepository {

    fun getRegister(registerEntity: RegisterEntity): MutableLiveData<RegisterEntity> {
        val json = JSONObject()
        val mutableLiveData = MutableLiveData<RegisterEntity>()
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val responseBodyCallApi = ApiClient().getApi().register(requestBody)
        responseBodyCallApi.enqueue(object  : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.body() != null) {
                    try {
                        val responseBody = response.body().toString()
                        val jsonObject = JSONObject(responseBody)
                        if (json.getString("status") == "Sukses"){
                            mutableLiveData.value = RegisterEntity(
                                full_name = jsonObject.getString("full_name"),
                                email = jsonObject.getString("email"),
                                birth_date = jsonObject.getString("birth_date"),
                                phone_number = jsonObject.getString("phone_number"),
                                password_digest = jsonObject.getString("password_digest"),
                                gender = jsonObject.getString("gender")
                            )
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mutableLiveData.postValue(error(t.message.toString()))
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
                if (response.body() != null) {
                    try {
                        mutableLiveData.value = response.body()

//                        val jsonObject = JSONObject(responseBody)
//                        if (json.getString("status") == "Sukses"){
//                            mutableLiveData.value = UserResponse(
//                                user = UserEntity(
//                                    id = jsonObject.getInt("id"),
//
//                                ),
//                            )
//                            mutableLiveData.value = LoginEntity(
//                                email = jsonObject.getString("email"),
//                                password_digest = jsonObject.getString("password_digest"),
//                                token = jsonObject.getString("token")
//                            )
//                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                mutableLiveData.value = UserResponse(error = t.toString())
            }

        })
        return mutableLiveData
    }


}