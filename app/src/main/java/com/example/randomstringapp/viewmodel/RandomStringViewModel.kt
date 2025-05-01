package com.example.randomstringapp.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomstringapp.model.RandomString
import com.example.randomstringapp.RandomStringRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RandomStringViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RandomStringRepository(application.applicationContext)

    private val _strings = MutableStateFlow<List<RandomString>>(emptyList())
    val strings: StateFlow<List<RandomString>> = _strings

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateString(length: Int) {
        viewModelScope.launch {
            val result = repository.getRandomString(length)
            if (result != null) {
                _strings.value = listOf(result) + _strings.value
            } else {
                _error.value = "Failed to generate string"
            }
        }
    }

    fun clearAll() {
        _strings.value = emptyList()
    }

    fun deleteAt(index: Int) {
        _strings.value = _strings.value.toMutableList().also { it.removeAt(index) }
    }

    fun clearError() {
        _error.value = null
    }
}
