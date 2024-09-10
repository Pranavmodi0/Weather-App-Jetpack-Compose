package com.only.practiceapp.domain.models

import com.only.practiceapp.utils.Utils


data class CurrentWeather(
    val temperature: Double,
    val time: String,
    val weatherStatus: Utils.WeatherInfoItem,
    val windDirection: String,
    val windSpeed: Double,
    val isDay: Boolean
)
