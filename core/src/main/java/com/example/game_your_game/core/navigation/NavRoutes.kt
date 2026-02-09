package com.example.game_your_game.core.navigation

object NavRoutes {
    const val GENRES = "genres"
    const val GAMES_LIST = "games_list/{genreId}"
    const val GAME_DETAILS = "game_details/{gameId}"

    fun gamesList(genreId: Int): String = "games_list/$genreId"
    fun gameDetails(gameId: Int): String = "game_details/$gameId"
}
