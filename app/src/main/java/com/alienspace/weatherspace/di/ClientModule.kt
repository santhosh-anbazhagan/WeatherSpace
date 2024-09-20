package com.alienspace.weatherspace.di

import com.alienspace.weatherspace.data.remote.OpenMeteoServiceAPI
import com.google.firebase.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ClientModule {

    @Provides
    @Singleton
    internal fun providesJson(): Json = Json { ignoreUnknownKeys = true }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder().baseUrl("https://api.open-meteo.com").client(client)
            .addConverterFactory(json.asConverterFactory(contentType = contentType)).build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(timeout = 60L, TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()
    }


    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )
    }

    @Singleton
    @Provides
    fun provideOpenMeteoServiceAPI(retrofit: Retrofit): OpenMeteoServiceAPI {
        return retrofit.create(OpenMeteoServiceAPI::class.java)
    }

}