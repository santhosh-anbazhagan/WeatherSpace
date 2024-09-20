package com.alienspace.weatherspace.di

import com.alienspace.weatherspace.data.repo.WeatherRepository
import com.alienspace.weatherspace.data.repo.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    fun provideWeatherRepository(weatherRepository: WeatherRepositoryImpl): WeatherRepository
}