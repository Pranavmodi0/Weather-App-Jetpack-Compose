package com.only.practiceapp.data.mapper_impl

import com.only.practiceapp.data.mappers.ApiMapper
import com.only.practiceapp.data.remote.models.ApiCurrentWeather
import com.only.practiceapp.data.remote.models.ApiDailyWeather
import com.only.practiceapp.data.remote.models.ApiHourlyWeather
import com.only.practiceapp.data.remote.models.ApiWeather
import com.only.practiceapp.di.ApiCurrentWeatherMapperAnnotation
import com.only.practiceapp.di.ApiDailyMapperAnnotation
import com.only.practiceapp.di.ApiHourlyWeatherMapperAnnotation
import com.only.practiceapp.domain.models.CurrentWeather
import com.only.practiceapp.domain.models.Daily
import com.only.practiceapp.domain.models.Hourly
import com.only.practiceapp.domain.models.Weather
import javax.inject.Inject

class ApiWeatherMapper @Inject constructor(
    @ApiDailyMapperAnnotation private val apiDailyMapper: ApiMapper<Daily, ApiDailyWeather>,
    @ApiCurrentWeatherMapperAnnotation private val apiCurrentWeatherMapper: ApiMapper<CurrentWeather, ApiCurrentWeather>,
    @ApiHourlyWeatherMapperAnnotation private val apiHourlyMapper: ApiMapper<Hourly, ApiHourlyWeather>
):ApiMapper<Weather, ApiWeather> {
    override fun mapToDomain(entity: ApiWeather): Weather {
        return Weather(
            currentWeather = apiCurrentWeatherMapper.mapToDomain(entity.current),
            daily = apiDailyMapper.mapToDomain(entity.daily),
            hourly = apiHourlyMapper.mapToDomain(entity.hourly)
        )
    }
}