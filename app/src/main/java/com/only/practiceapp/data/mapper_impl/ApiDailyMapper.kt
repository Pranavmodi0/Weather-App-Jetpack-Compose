package com.only.practiceapp.data.mapper_impl

import com.only.practiceapp.data.mappers.ApiMapper
import com.only.practiceapp.data.remote.models.ApiDailyWeather
import com.only.practiceapp.domain.models.Daily
import com.only.practiceapp.utils.Utils

class ApiDailyMapper: ApiMapper<Daily, ApiDailyWeather> {
    override fun mapToDomain(entity: ApiDailyWeather): Daily {
        return Daily(
            temperatureMax = entity.temperature2mMax,
            temperatureMin = entity.temperature2mMin,
            time = parseTime(entity.time),
            weatherStatus = parseWeatherStatus(entity.weatherCode),
            windDirection = parseWindDirection(entity.windDirection10mDominant),
            sunset = entity.sunset.map { Utils.formatUnixData("HH:mm",it.toLong()) },
            sunrise = entity.sunrise.map { Utils.formatUnixData("HH:mm",it.toLong()) },
            uvIndex = entity.uvIndexMax,
            windSpeed = entity.windSpeed10mMax
        )
    }

    private fun parseTime(time: List<Long>): List<String>{
        return time.map {
            Utils.formatUnixData("E", it)
        }
    }

    private fun parseWeatherStatus(code: List<Int>): List<Utils.WeatherInfoItem>{
        return code.map {
            Utils.getWeatherInfo(it)
        }
    }

    private fun parseWindDirection(windDirection: List<Double>): List<String> {
        return windDirection.map {
            Utils.getWindDirection(it)
        }
    }
}