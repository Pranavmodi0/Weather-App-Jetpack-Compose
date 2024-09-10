package com.only.practiceapp.domain.repository

import com.only.practiceapp.domain.models.Weather
import com.only.practiceapp.utils.Response
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherData(): Flow<Response<Weather>>
}