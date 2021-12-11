package com.example.alodokter_rakamin_android_kelompok1.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    // TODO: Implement the ViewModel


    fun getData() = viewModelScope.launch(Dispatchers.IO) {

    }
}