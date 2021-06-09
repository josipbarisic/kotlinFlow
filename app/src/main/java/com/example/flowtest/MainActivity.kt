package com.example.flowtest

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.flowtest.databinding.ActivityMainBinding
import com.example.flowtest.repo.MainRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private val mainViewModel = MainViewModel(MainRepo())
    private val scope = CoroutineScope(Dispatchers.Main)

    companion object {
        const val TOMORROW_TAG = "TOMORROW"
    }

    private val weatherObserver = Observer<String> {
        viewBinding.tvTemperature.textSize = if (it.toIntOrNull() == null) 30f else 75f
        viewBinding.tempIcon.visibility = if (it.toIntOrNull() == null) View.GONE else View.VISIBLE
        viewBinding.tvTemperature.text = it
    }

    private val realtimeWeatherObserver = Observer<String> {
        viewBinding.tvRealtimeTemperature.textSize = if (it.toIntOrNull() == null) 30f else 75f
        viewBinding.realtimeTempIcon.visibility =
            if (it.toIntOrNull() == null) View.GONE else View.VISIBLE
        viewBinding.tvRealtimeTemperature.text = it
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setUpObservers()
    }

    private fun setUpObservers() {
        scope.launch {
            mainViewModel.tomorrowWeatherFlow
                .onEach {
                    Log.d(TOMORROW_TAG, "onEach Thread: ${Thread.currentThread().name}")
                    delay(5000)
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    Log.d(TOMORROW_TAG, "collect Thread: ${Thread.currentThread().name}")
                    viewBinding.tomorrowTempIcon.visibility = View.VISIBLE
                    viewBinding.tvTomorrowTemperature.text = it.toString()
                }
        }
        mainViewModel.weather.observe(this, weatherObserver)
        mainViewModel.realtimeWeather.observe(this, realtimeWeatherObserver)
    }
}