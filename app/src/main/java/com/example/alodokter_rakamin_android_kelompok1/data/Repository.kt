package com.example.alodokter_rakamin_android_kelompok1.data

import androidx.lifecycle.MutableLiveData
import com.example.alodokter_rakamin_android_kelompok1.api.ApiClient
import com.example.alodokter_rakamin_android_kelompok1.data.entity.UserEntity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    //fun getLogin(userEntity: UserEntity): MutableLiveData<UserEntity> {
    //  val json = JSONObject()
    //  json.put("email", userEntity.email)
    //  json.put("password_digest", userEntity.password_digest)
    //  val mutableLiveData = MutableLiveData<UserEntity>()

    //  val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
    //  val responseBodyCall = ApiClient().getApi().login(requestBody)
    //  responseBodyCall.enqueue(object : Callback<ResponseBody> {
    //    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
    //        if (response.body() != null) {
    //        try {
    //              val responses = response.body()!!.string()
    //              var jsonObject = JSONObject(responses)
    //          if (jsonObject.getString("status_message_ind") == "created_at") {
    //              jsonObject = jsonObject.getJSONObject("value")
    //              mutableLiveData.value = UserEntity(
    //                  full_name = jsonObject.getString("full_name"),
    //                  age = jsonObject.getInt("age"),
    //                  gender = jsonObject.getString("gender"),
    //                  birth_date = jsonObject.getString("birth_date"),
    //                  phone_number = jsonObject.getString("phone_number"),
    //                  image_path = jsonObject.getString("image_path"),
    //                  email = jsonObject.getString("email"),
    //                  noTelp = jsonObject.getString("phone"),
    //                  customerName = jsonObject.getString("customer_name"),
    //          sukses = true
    //      )
    //   } else {
    //          mutableLiveData.value = UserEntity(
    //          sukses = false
    //    )
    //  }

    //  } catch (e: IOException)
    //{
    //    e.printStackTrace()
    //  } catch (e: JSONException)
    // {
    //   e.printStackTrace()
    //  }
    //}
    //}

    //override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
    //
    //   Log.v("jajal", t.message + " a")
    //}
    //})
    //return mutableLiveData
    //}
}