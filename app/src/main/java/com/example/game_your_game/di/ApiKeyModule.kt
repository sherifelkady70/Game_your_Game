package com.example.game_your_game.di

import com.example.game_your_game.BuildConfig
import com.example.game_your_game.core.di.RawgApiKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiKeyModule {

    @Provides
    @Singleton
    @RawgApiKey
    fun provideRawgApiKey(): String =
        (BuildConfig.RAWG_API_KEY ?: "").takeIf { it.isNotBlank() && it != "null" } ?: ""
}
