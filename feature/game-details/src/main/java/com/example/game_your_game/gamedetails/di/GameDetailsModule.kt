package com.example.game_your_game.gamedetails.di

import com.example.game_your_game.gamedetails.data.mapper.GameDetailsMapper
import com.example.game_your_game.gamedetails.data.mapper.GameDetailsMapperImpl
import com.example.game_your_game.gamedetails.data.remote.GameDetailsApi
import com.example.game_your_game.gamedetails.data.repository.GameDetailsRepositoryImpl
import com.example.game_your_game.gamedetails.domain.repository.GameDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GameDetailsModule {

    @Binds
    @Singleton
    abstract fun bindGameDetailsRepository(impl: GameDetailsRepositoryImpl): GameDetailsRepository

    @Binds
    @Singleton
    abstract fun bindGameDetailsMapper(impl: GameDetailsMapperImpl): GameDetailsMapper

    companion object {
        @Provides
        @Singleton
        fun provideGameDetailsApi(retrofit: Retrofit): GameDetailsApi =
            retrofit.create(GameDetailsApi::class.java)
    }
}
