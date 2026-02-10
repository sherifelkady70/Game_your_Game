package com.example.game_your_game.genres.data.remote

import com.example.game_your_game.core.utilits.Constants
import com.example.game_your_game.genres.data.remote.dto.GenresResponseDto
import retrofit2.http.GET


interface GenresApi {

    @GET(Constants.GENRES)
    suspend fun getGenres(): GenresResponseDto
}
