package com.example.flowtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.flowtest.data.Result
import com.example.flowtest.repo.MainRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map

class MainViewModel(mainRepo: MainRepo) : ViewModel() {
    private val _weatherFlow = mainRepo.fetchWeather()
    private val _weather = _weatherFlow
        .map { temp ->
            when (temp) {
                is Result.Loading -> "Loading..."
                is Result.Success -> "${temp.data}"
                is Result.Error -> "Error fetching temperature"
            }
        }
        .asLiveData(Dispatchers.Default + viewModelScope.coroutineContext)
    val weather: LiveData<String>
        get() = _weather

    private val _realtimeWeatherFlow = mainRepo.fetchRealtimeWeather()
    private val _realtimeWeather = _realtimeWeatherFlow
        .map { temp ->
            when (temp) {
                is Result.Loading -> "Loading..."
                is Result.Success -> "${temp.data}"
                is Result.Error -> "Error: ${temp.exception.message}"
            }
        }
        .asLiveData(Dispatchers.Default + viewModelScope.coroutineContext)
    val realtimeWeather: LiveData<String>
        get() = _realtimeWeather

    val tomorrowWeatherFlow = mainRepo.fetchTomorrowWeather()
}