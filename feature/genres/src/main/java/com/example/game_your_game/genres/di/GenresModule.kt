package com.example.game_your_game.genres.di

import com.example.game_your_game.genres.data.remote.GenresApi
import com.example.game_your_game.genres.data.repository.GenresRepositoryImpl
import com.example.game_your_game.genres.domain.repository.GenresRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GenresModule {

    @Binds
    @Singleton
    abstract fun bindGenresRepository(impl: GenresRepositoryImpl): GenresRepository

    companion object {
        @Provides
        @Singleton
        fun provideGenresApi(retrofit: Retrofit): GenresApi =
            retrofit.create(GenresApi::class.java)
    }
}
