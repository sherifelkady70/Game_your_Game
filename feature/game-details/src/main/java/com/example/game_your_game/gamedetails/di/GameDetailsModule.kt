package com.example.game_your_game.gamedetails.di

import android.content.Context
import androidx.room.Room
import com.example.game_your_game.gamedetails.data.local.GameDetailsDatabase
import com.example.game_your_game.gamedetails.data.local.dao.GameDetailsDao
import com.example.game_your_game.gamedetails.data.remote.GameDetailsApi
import com.example.game_your_game.gamedetails.data.repository.GameDetailsRepositoryImpl
import com.example.game_your_game.gamedetails.domain.repository.GameDetailsRepository
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
abstract class GameDetailsModule {

    @Binds
    @Singleton
    abstract fun bindGameDetailsRepository(impl: GameDetailsRepositoryImpl): GameDetailsRepository

    companion object {
        @Provides
        @Singleton
        fun provideGameDetailsApi(retrofit: Retrofit): GameDetailsApi =
            retrofit.create(GameDetailsApi::class.java)

        @Provides
        @Singleton
        fun provideGameDetailsDatabase(@ApplicationContext context: Context): GameDetailsDatabase =
            Room.databaseBuilder(
                context,
                GameDetailsDatabase::class.java,
                "game_details_db"
            ).build()

        @Provides
        @Singleton
        fun provideGameDetailsDao(database: GameDetailsDatabase): GameDetailsDao =
            database.gameDetailsDao()
    }
}
