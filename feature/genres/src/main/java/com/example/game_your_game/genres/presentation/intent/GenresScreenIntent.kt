package com.example.game_your_game.genres.presentation.intent

import com.example.game_your_game.genres.domain.model.Genre

sealed class GenresScreenIntent {
    data object OnRetry : GenresScreenIntent()
    data class OnGenresClicked(val genreId: Int) : GenresScreenIntent()

}


sealed class GenresScreenEffect {
    data class NavigateToGameList(val genreId: Int) : GenresScreenEffect()
}
sealed interface GenresState {
    data object Loading : GenresState
    data class Success(val genres: List<Genre>) : GenresState
    data class Error(val message: String) : GenresState
    data class Empty(val message: String) : GenresState

}