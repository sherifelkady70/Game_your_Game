package com.example.game_your_game.games.di

import com.example.game_your_game.games.data.remote.GamesApi
import com.example.game_your_game.games.data.repository.GamesRepositoryImpl
import com.example.game_your_game.games.domain.repository.GamesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GamesModule {

    @Binds
    @Singleton
    abstract fun bindGamesRepository(impl: GamesRepositoryImpl): GamesRepository

    companion object {
        @Provides
        @Singleton
        fun provideGamesApi(retrofit: Retrofit): GamesApi =
            retrofit.create(GamesApi::class.java)
    }
}
