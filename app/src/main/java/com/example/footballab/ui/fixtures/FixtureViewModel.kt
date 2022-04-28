package com.example.footballab.ui.fixtures


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FixtureViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Fixture Fragment"
    }
    val text: LiveData<String> = _text
}