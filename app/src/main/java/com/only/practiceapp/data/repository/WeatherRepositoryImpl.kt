package com.only.practiceapp.data.repository

import com.only.practiceapp.data.mapper_impl.ApiWeatherMapper
import com.only.practiceapp.data.remote.WeatherInterface
import com.only.practiceapp.domain.models.Weather
import com.only.practiceapp.domain.repository.WeatherRepository
import com.only.practiceapp.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherInterface,
    private val apiWeatherMapper: ApiWeatherMapper
): WeatherRepository {
    override fun getWeatherData(): Flow<Response<Weather>> = flow {
        emit(Response.Loading())
        val apiWeather = weatherApi.getWeatherData()
        val weather = apiWeatherMapper.mapToDomain(apiWeather)
        emit(Response.Success(weather))
    }.catch {e ->
        e.printStackTrace()
        emit(Response.Error(e.message.orEmpty()))
    }
}