package com.example.game_your_game.games.presentation.intent

import com.example.game_your_game.games.domain.model.Game

sealed class GamesScreenIntent {
    data object OnRetry : GamesScreenIntent()
    data class OnScrollPosition(val lastVisibleIndex: Int, val totalItems: Int) : GamesScreenIntent()

    data class OnGameClicked(val gameId: Int) : GamesScreenIntent()

}

sealed class GamesScreenEffect {
    data class NavigateToGameDetails(val gameId: Int) : GamesScreenEffect()
}
sealed interface GamesListState {
    data object Loading : GamesListState
    data class Success(
        val games: List<Game>,
        val isLoadingMore: Boolean = false,
        val hasMore: Boolean = true
    ) : GamesListState
    data class Error(val message: String) : GamesListState
    data class Empty(val message: String) : GamesListState
}
