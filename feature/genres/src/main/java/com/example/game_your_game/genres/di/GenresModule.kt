package com.example.game_your_game.genres.di

import com.example.game_your_game.genres.data.mapper.GenreMapper
import com.example.game_your_game.genres.data.mapper.GenreMapperImpl
import com.example.game_your_game.genres.data.remote.GenresApi
import com.example.game_your_game.genres.data.repository.GenresRepositoryImpl
import com.example.game_your_game.genres.domain.repository.GenresRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class GenresModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGenresRepository(impl: GenresRepositoryImpl): GenresRepository

    @Binds
    @ViewModelScoped
    abstract fun bindGenreMapper(impl: GenreMapperImpl): GenreMapper

    companion object {
        @Provides
        @ViewModelScoped
        fun provideGenresApi(retrofit: Retrofit): GenresApi =
            retrofit.create(GenresApi::class.java)
    }
}
