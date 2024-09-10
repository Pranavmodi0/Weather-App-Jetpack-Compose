package com.only.practiceapp.data.mapper_impl

import com.only.practiceapp.data.mappers.ApiMapper
import com.only.practiceapp.data.remote.models.ApiHourlyWeather
import com.only.practiceapp.domain.models.Hourly
import com.only.practiceapp.utils.Utils

class ApiHourlyMapper: ApiMapper<Hourly, ApiHourlyWeather> {
    override fun mapToDomain(entity: ApiHourlyWeather): Hourly {
        return Hourly(
            temperature = entity.temperature2m,
            time = parseTime(entity.time),
            weatherStatus = parseWeatherStatus(entity.weatherCode)
        )
    }

    private fun parseTime(time: List<Long>): List<String>{
        return time.map { Utils.formatUnixData("HH:mm", it) }
    }

    private fun parseWeatherStatus(code: List<Int>): List<Utils.WeatherInfoItem>{
        return code.map { Utils.getWeatherInfo(it) }
    }
}