package com.only.practiceapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.only.practiceapp.domain.models.Daily
import com.only.practiceapp.domain.models.Weather
import com.only.practiceapp.domain.repository.WeatherRepository
import com.only.practiceapp.utils.Response
import com.only.practiceapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    var homeState by mutableStateOf(HomeState())
        private set

    init {
        viewModelScope.launch {
            repository.getWeatherData().collect{response ->
                when(response){
                    is Response.Loading -> {
                        homeState = homeState.copy(
                            isLoading = true
                        )
                    }

                    is Response.Success -> {
                        homeState = homeState.copy(
                            isLoading = false,
                            error = null,
                            weather = response.data
                        )
                        val todayDailyWeatherInfo = response.data?.daily?.weatherInfo?.find {
                            Utils.isTodayDate(it.time)
                        }
                        homeState = homeState.copy(
                            dailyWeatherInfo = todayDailyWeatherInfo
                        )
                    }

                    is Response.Error -> {
                        homeState = homeState.copy(
                            isLoading = false,
                            error = response.message
                        )
                    }
                }
            }
        }
    }
}

data class HomeState(
    val weather: Weather? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val dailyWeatherInfo: Daily.WeatherInfo? = null
)