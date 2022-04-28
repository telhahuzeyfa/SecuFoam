package com.example.footballab.ui.newsMaps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapsNewsViewModel: ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Notification Fragment"
    }
    val text: LiveData<String> = _text
}