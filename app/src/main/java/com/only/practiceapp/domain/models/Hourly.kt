package com.only.practiceapp.domain.models

import com.only.practiceapp.utils.Utils

data class Hourly(
    private val temperature: List<Double>,
    private val time: List<String>,
    private val weatherStatus: List<Utils.WeatherInfoItem>,
){

    val weatherInfo: List<HourlyInfoItem>
        get() {
            return time.mapIndexed { index, time ->
                HourlyInfoItem(
                    temperature = temperature[index],
                    time = time,
                    weatherStatus = weatherStatus[index]
                )
            }
        }

    data class HourlyInfoItem(
        val temperature: Double,
        val time: String,
        val weatherStatus: Utils.WeatherInfoItem
    )
}