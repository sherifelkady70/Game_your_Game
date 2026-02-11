package com.example.game_your_game.di

import android.content.Context
import androidx.room.Room
import com.example.game_your_game.data.local.AppDatabase
import com.example.game_your_game.gamedetails.data.local.dao.GameDetailsDao
import com.example.game_your_game.genres.data.local.dao.GenresDao
import com.example.game_your_game.games.data.local.dao.GamesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "game_your_game_db"

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideGenresDao(database: AppDatabase): GenresDao =
        database.genresDao()

    @Provides
    @Singleton
    fun provideGamesDao(database: AppDatabase): GamesDao =
        database.gamesDao()

    @Provides
    @Singleton
    fun provideGameDetailsDao(database: AppDatabase): GameDetailsDao =
        database.gameDetailsDao()
}
