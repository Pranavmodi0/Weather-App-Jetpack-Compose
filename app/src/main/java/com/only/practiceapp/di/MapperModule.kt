package com.only.practiceapp.di

import com.only.practiceapp.data.mapper_impl.ApiDailyMapper
import com.only.practiceapp.data.mapper_impl.ApiHourlyMapper
import com.only.practiceapp.data.mapper_impl.ApiWeatherMapper
import com.only.practiceapp.data.mapper_impl.CurrentWeatherMapper
import com.only.practiceapp.data.mappers.ApiMapper
import com.only.practiceapp.data.remote.models.ApiCurrentWeather
import com.only.practiceapp.data.remote.models.ApiDailyWeather
import com.only.practiceapp.data.remote.models.ApiHourlyWeather
import com.only.practiceapp.data.remote.models.ApiWeather
import com.only.practiceapp.domain.models.CurrentWeather
import com.only.practiceapp.domain.models.Daily
import com.only.practiceapp.domain.models.Hourly
import com.only.practiceapp.domain.models.Weather
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @ApiDailyMapperAnnotation
    @Provides
    fun provideApiWeatherMapper(): ApiMapper<Daily, ApiDailyWeather>{
        return ApiDailyMapper()
    }

    @ApiCurrentWeatherMapperAnnotation
    @Provides
    fun provideCurrentWeatherMapper(): ApiMapper<CurrentWeather, ApiCurrentWeather>{
        return CurrentWeatherMapper()
    }

    @ApiHourlyWeatherMapperAnnotation
    @Provides
    fun provideHourlyMapper(): ApiMapper<Hourly, ApiHourlyWeather>{
        return ApiHourlyMapper()
    }

    @ApiWeatherMapperAnnotation
    @Provides
    fun provideWeatherMapper(
        apiDailyMapper: ApiMapper<Daily, ApiDailyWeather>,
        apiCurrentWeatherMapper: ApiMapper<CurrentWeather, ApiCurrentWeather>,
        apiHourlyMapper: ApiMapper<Hourly, ApiHourlyWeather>
    ): ApiMapper<Weather, ApiWeather>{
        return ApiWeatherMapper(
            apiDailyMapper,
            apiCurrentWeatherMapper,
            apiHourlyMapper
        )
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiDailyMapperAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiWeatherMapperAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiCurrentWeatherMapperAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiHourlyWeatherMapperAnnotation