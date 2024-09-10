package com.only.practiceapp.data.mapper_impl

import com.only.practiceapp.data.mappers.ApiMapper
import com.only.practiceapp.data.remote.models.ApiCurrentWeather
import com.only.practiceapp.domain.models.CurrentWeather
import com.only.practiceapp.utils.Utils

class CurrentWeatherMapper: ApiMapper<CurrentWeather, ApiCurrentWeather> {
    override fun mapToDomain(entity: ApiCurrentWeather): CurrentWeather {
        return CurrentWeather(
            temperature = entity.temperature2m,
            time = parseTime(entity.time),
            weatherStatus = parseWeatherStatus(entity.weatherCode),
            windDirection = parseWindDirection(entity.windDirection10m),
            windSpeed = entity.windSpeed10m,
            isDay = entity.isDay == 1
        )
    }

    private fun parseTime(time: Long): String{
        return Utils.formatUnixData("HH:mm", time)
    }

    private fun parseWeatherStatus(code: Int): Utils.WeatherInfoItem{
        return Utils.getWeatherInfo(code)
    }

    private fun parseWindDirection(windDirection: Double): String{
        return Utils.getWindDirection(windDirection)
    }

}