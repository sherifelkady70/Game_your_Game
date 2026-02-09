package com.example.game_your_game.di

import com.example.game_your_game.BuildConfig
import com.example.game_your_game.data.remote.GameApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.rawg.io/api/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        // Null-safe and ignore literal "null" string (from missing gradle property)
        val apiKey = (BuildConfig.RAWG_API_KEY ?: "")
            .takeIf { it.isNotBlank() && it != "null" }
            ?: ""
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val newUrl = if (apiKey.isNotBlank()) {
                    original.url.newBuilder().addQueryParameter("key", apiKey).build()
                } else {
                    original.url
                }
                chain.proceed(original.newBuilder().url(newUrl).build())
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): GameApiServices =
        retrofit.create(GameApiServices::class.java)
}

