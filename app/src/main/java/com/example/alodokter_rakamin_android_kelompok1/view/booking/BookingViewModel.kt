package com.example.alodokter_rakamin_android_kelompok1.view.booking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alodokter_rakamin_android_kelompok1.api.ApiResponse
import com.example.alodokter_rakamin_android_kelompok1.data.entity.DoctorEntity
import com.example.alodokter_rakamin_android_kelompok1.data.repository.DoctorRepository
import com.example.alodokter_rakamin_android_kelompok1.data.repository.UserRepository
import com.example.alodokter_rakamin_android_kelompok1.data.response.DataResponse
import com.example.alodokter_rakamin_android_kelompok1.data.response.MetaResponse

class BookingViewModel : ViewModel() {
    private lateinit var repository: DoctorRepository
    private lateinit var userRepository: UserRepository
    private var page = 1
    private var totalPage = 1
    private var check = 1
    private var text = ""

    fun getAllDoctors(page: Int = 1, perPage: Int = 10) : MutableLiveData<ApiResponse<DataResponse<DoctorEntity>>> {
        check = 1
        return repository.getDoctors(page, perPage)
    }

    fun setRepository(doctorRepository: DoctorRepository){
        repository = doctorRepository
    }

    fun setUserRepository(userRepository: UserRepository){
        this.userRepository = userRepository
    }

    fun getUser(token: String) = userRepository.getUser(token)

    fun setPage(metaResponse: MetaResponse){
        totalPage = metaResponse.totalPage
    }

    fun resetData(){
        page = 1
        totalPage = 1
        text = ""
    }

    fun loadNewDoctors() : MutableLiveData<ApiResponse<DataResponse<DoctorEntity>>> {
        return if(totalPage > page){
            page++
            when(check){
                1 -> getAllDoctors(page)
                2 -> searchArticles(page,query = text)
                3 -> getByCategory(page, category = text)
                else -> getAllDoctors(page)
            }
        } else {
            val data : MutableLiveData<ApiResponse<DataResponse<DoctorEntity>>> = MutableLiveData()
            data.value = ApiResponse.Error("Last Doctors")
            data
        }
    }

    fun searchArticles(page:Int = 1, perPage: Int = 10,query:String) : MutableLiveData<ApiResponse<DataResponse<DoctorEntity>>> {
        check = 2
        text = query
        return repository.searchDoctorsTitle(page, perPage, query)
    }

    fun getByCategory(page: Int = 1, perPage: Int = 10, category: String) : MutableLiveData<ApiResponse<DataResponse<DoctorEntity>>>
    {
        check = 3
        text = category
        return repository.searchDoctorsBySpeciality(page, perPage, category)
    }

}