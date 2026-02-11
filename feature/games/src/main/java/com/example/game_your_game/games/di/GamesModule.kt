package com.example.game_your_game.games.di

import com.example.game_your_game.games.data.mapper.GameMapper
import com.example.game_your_game.games.data.mapper.GameMapperImpl
import com.example.game_your_game.games.data.remote.GamesApi
import com.example.game_your_game.games.data.repository.GamesRepositoryImpl
import com.example.game_your_game.games.domain.repository.GamesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class GamesModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGamesRepository(impl: GamesRepositoryImpl): GamesRepository

    @Binds
    @ViewModelScoped
    abstract fun bindGameMapper(impl: GameMapperImpl): GameMapper

    companion object {
        @Provides
        @ViewModelScoped
        fun provideGamesApi(retrofit: Retrofit): GamesApi =
            retrofit.create(GamesApi::class.java)
    }
}
