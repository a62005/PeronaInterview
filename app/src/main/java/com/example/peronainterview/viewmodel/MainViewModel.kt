package com.example.peronainterview.viewmodel

import androidx.lifecycle.*
import com.example.peronainterview.database.WeatherForecast
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _weatherList = MutableLiveData<List<WeatherForecast>>()
    val weatherList: LiveData<List<WeatherForecast>> get() = _weatherList

    init {
        viewModelScope.launch {
            _weatherList.value = repository.getWeatherList()
        }
    }

    fun getLaunchTimes() = repository.getLaunchTimes()

}

class MainViewModelFactory(private val repository: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}