package com.only.practiceapp.data.remote

import com.only.practiceapp.data.remote.models.ApiWeather
import com.only.practiceapp.utils.ApiParameters
import com.only.practiceapp.utils.Key
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {

    @GET(Key.END_POINT)
    suspend fun getWeatherData(
        @Query(ApiParameters.LATITUDE) latitude: Float = 0.1F,
        @Query(ApiParameters.LONGITUDE) longitude: Float = 1.1f,
        @Query(ApiParameters.DAILY) daily: Array<String> = arrayOf(
            "weather_code",
            "temperature_2m_max",
            "temperature_2m_min",
            "wind_speed_10m_max",
            "wind_direction_10m_dominant",
            "sunrise",
            "sunset",
            "uv_index_max"
        ),
        @Query(ApiParameters.CURRENT_WEATHER) currentWeather: Array<String> = arrayOf(
            "temperature_2m",
            "is_day",
            "weather_code",
            "wind_speed_10m",
            "wind_direction_10m"
        ),
        @Query(ApiParameters.HOURLY) hourly: Array<String> = arrayOf(
            "temperature_2m",
            "weather_code",
        ),
        @Query(ApiParameters.TIME_FORMAT) timeFormat: String = "unixtime",
        @Query(ApiParameters.TIMEZONE) timezone: String = "auto"
    ): ApiWeather
}