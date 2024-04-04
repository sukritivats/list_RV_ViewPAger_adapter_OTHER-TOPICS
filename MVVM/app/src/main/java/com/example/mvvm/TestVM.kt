package com.example.mvvm

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestVM : ViewModel() {

    val observableString: ObservableField<String> = ObservableField("")

    // live data
    val liveDataVariable = MutableLiveData<String>("Previous Value")

    private val _liveData: MutableLiveData<String> = MutableLiveData()
    val liveData: LiveData<String> = _liveData

    private var count = 0
    fun update(view: View) = viewModelScope.launch(Dispatchers.IO) {

        while (true) {
            delay(1000)
            count++
            liveDataVariable.postValue(count.toString())
        }

    }

}