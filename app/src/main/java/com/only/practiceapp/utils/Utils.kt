package com.only.practiceapp.utils

import androidx.annotation.DrawableRes
import com.only.practiceapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils{
    private val DIRECTIONS = listOf(
        "North",
        "North East",
        "East",
        "South East",
        "South",
        "South West",
        "West",
        "North West"
    )

    fun formatUnixData(pattern: String, time: Long): String{
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(time * 1000)
    }

    fun formatNormalDate(pattern: String, time: Long): String{
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(time)
    }

    fun getWindDirection(windDirection: Double): String{
        return DIRECTIONS[(windDirection % 360 / 45 % 8).toInt()]
    }

    fun getWeatherInfo(code: Int): WeatherInfoItem {
        return when (code) {
            0 -> WeatherInfoItem("Clear sky", R.drawable.sun)
            1 -> WeatherInfoItem("Mainly clear", R.drawable.sun)
            2 -> WeatherInfoItem("partly cloudy", R.drawable.partly_cloudy)
            3 -> WeatherInfoItem("overcast", R.drawable.partly_cloudy)
            45, 48 -> WeatherInfoItem("Fog", R.drawable.partly_cloudy)
            51, 53, 55,
            -> WeatherInfoItem("Drizzle", R.drawable.partly_cloudy)

            56, 57 -> WeatherInfoItem("Freezing Drizzle", R.drawable.partly_cloudy)
            61,
            -> WeatherInfoItem("Rain: Slight", R.drawable.partly_cloudy)

            63 -> WeatherInfoItem("Rain: Moderate", R.drawable.partly_cloudy)
            65 -> WeatherInfoItem("Rain: Heavy", R.drawable.partly_cloudy)
            66, 67 -> WeatherInfoItem("Freezing Rain", R.drawable.partly_cloudy)
            71 -> WeatherInfoItem("Snow fall: Slight", R.drawable.partly_cloudy)
            73 -> WeatherInfoItem("Snow fall: moderate", R.drawable.partly_cloudy)
            75 -> WeatherInfoItem("Snow fall: Heavy", R.drawable.partly_cloudy)
            77 -> WeatherInfoItem("Snow grains", R.drawable.partly_cloudy)
            80, 81, 82 -> WeatherInfoItem("Rain showers: Slight", R.drawable.partly_cloudy)
            85, 86 -> WeatherInfoItem("Snow showers slight", R.drawable.partly_cloudy)
            95, 96, 99 -> WeatherInfoItem("Thunderstorm: Slight", R.drawable.partly_cloudy)
            else -> WeatherInfoItem("Unknown", R.drawable.partly_cloudy)
        }
    }

    fun isTodayDate(day: String): Boolean {
        val todayDate = formatNormalDate("E", Date().time)
        return todayDate.lowercase() == day.lowercase()
    }


    data class WeatherInfoItem(
        val info: String,
        @DrawableRes val icon: Int
    )
}