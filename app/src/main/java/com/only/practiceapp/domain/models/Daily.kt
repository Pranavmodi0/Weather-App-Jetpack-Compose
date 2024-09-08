package com.only.practiceapp.domain.models

import com.only.practiceapp.utils.Utils

data class Daily (
    private val temperatureMax: List<Double>,
    private val temperatureMin: List<Double>,
    private val time: List<String>,
    private val weatherStatus: List<Utils.WeatherInfoItem>,
    private val windDirection: List<String>,
    private val windSpeed: List<Double>,
    private val sunrise: List<String>,
    private val sunset: List<String>,
    private val uvIndex: List<Double>
) {
    val weatherInfo: List<WeatherInfo>
        get(){
            val dailyWeatherInfo = mutableListOf<WeatherInfo>()
            for(i in temperatureMax.indices){
                dailyWeatherInfo.add(
                    WeatherInfo(
                        temperatureMax = temperatureMax[i],
                        temperatureMin = temperatureMin[i],
                        time = time[i],
                        weatherStatus = weatherStatus[i],
                        windDirection = windDirection[i],
                        windSpeed = windSpeed[i],
                        sunrise = sunrise[i],
                        sunset = sunset[i],
                        uvIndex = uvIndex[i]
                    )
                )
            }
            return dailyWeatherInfo
        }

    data class WeatherInfo(
        val temperatureMax: Double,
        val temperatureMin: Double,
        val time: String,
        val weatherStatus: Utils.WeatherInfoItem,
        val windDirection: String,
        val windSpeed: Double,
        val sunrise: String,
        val sunset: String,
        val uvIndex: Double
    )
}