package com.only.practiceapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.only.practiceapp.data.remote.WeatherInterface
import com.only.practiceapp.utils.Key
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideApi(builder:Retrofit.Builder):WeatherInterface{
        return builder
            .build()
            .create(WeatherInterface::class.java)
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit():Retrofit.Builder{
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(Key.API_BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
    }

}