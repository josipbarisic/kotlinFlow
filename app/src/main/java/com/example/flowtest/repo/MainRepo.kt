package com.example.flowtest.repo

import com.example.flowtest.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

class MainRepo {

    fun fetchWeather() = flow {
        emit(Result.Loading)
        delay(2000)
        emit(Result.Success((15..25).random()))
    }

    fun fetchRealtimeWeather() = flow {
        emit(Result.Loading)
        while (true) {
            delay(1000)
            val currentTemp = (10..30).random()
            if ((10..13).contains(currentTemp))
                emit(
                    Result.Error(
                        Exception("Temperature too low.")
                    )
                )
            else emit(Result.Success(currentTemp))
        }
    }

    fun fetchTomorrowWeather() = flowOf((10..35).random())
}