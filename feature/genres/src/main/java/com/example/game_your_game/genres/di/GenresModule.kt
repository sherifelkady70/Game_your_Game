package com.example.game_your_game.genres.di

import android.content.Context
import androidx.room.Room
import com.example.game_your_game.genres.data.local.GenresDatabase
import com.example.game_your_game.genres.data.local.dao.GenresDao
import com.example.game_your_game.genres.data.remote.GenresApi
import com.example.game_your_game.genres.data.repository.GenresRepositoryImpl
import com.example.game_your_game.genres.domain.repository.GenresRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

        @Provides
        @Singleton
        fun provideGenresDatabase(@ApplicationContext context: Context): GenresDatabase =
            Room.databaseBuilder(
                context,
                GenresDatabase::class.java,
                "genres_db"
            ).build()

        @Provides
        @Singleton
        fun provideGenresDao(database: GenresDatabase): GenresDao =
            database.genresDao()
    }
}
