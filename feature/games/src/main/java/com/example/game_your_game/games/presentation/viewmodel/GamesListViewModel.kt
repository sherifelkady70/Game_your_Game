package com.example.game_your_game.games.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.game_your_game.core.utilits.Constants
import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.games.presentation.intent.GamesScreenIntent
import com.example.game_your_game.games.domain.model.Game
import com.example.game_your_game.games.domain.usecase.GetGamesByGenreUseCase
import com.example.game_your_game.games.presentation.intent.GamesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class GamesListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGamesByGenreUseCase: GetGamesByGenreUseCase
) : ViewModel() {

    private val genreId: Int = checkNotNull(savedStateHandle[Constants.GENRE_ID]) { Constants.GENRE_ID_REQUIRED }
    private var currentPage = 1

    private val _state = MutableStateFlow<GamesListState>(GamesListState.Loading)
    val state: StateFlow<GamesListState> = _state.asStateFlow()

    init {
        loadGames()
    }

    fun setIntent(action: GamesScreenIntent) {
        when (action) {
            GamesScreenIntent.OnLoadMore -> loadMoreGames()
            GamesScreenIntent.OnRetry -> loadGames()
        }
    }
    private fun loadGames() {
        viewModelScope.launch {
            currentPage = 1
            _state.update { GamesListState.Loading }
            getGamesByGenreUseCase(genreId, currentPage).collect { result ->
                when (result) {
                    is NetworkStateResource.Success -> {
                        val list = result.data as List<Game>
                        _state.update {
                            GamesListState.Success(
                                games = list,
                                hasMore = list.size >= Constants.PAGE_SIZE
                            )
                        }
                    }
                    is NetworkStateResource.Error -> {
                        _state.update {
                            GamesListState.Error(result.throwable.message ?: Constants.GENERAL_ERROR_MESSAGE)
                        }
                    }
                    NetworkStateResource.Empty -> {
                        _state.update { GamesListState.Empty(Constants.EMPTY_MESSAGE) }
                    }
                    is NetworkStateResource.Loading -> {
                        _state.update { GamesListState.Loading }
                    }
                }
            }
        }
    }

    private fun loadMoreGames() {
        val current = _state.value
        if (current !is GamesListState.Success ||
            current.isLoadingMore ||
            !current.hasMore
        ) return

        viewModelScope.launch {
            val prevSize = current.games.size
            _state.update {
                (it as? GamesListState.Success)?.copy(isLoadingMore = true) ?: it
            }
            getGamesByGenreUseCase(genreId, currentPage + 1).collect { result ->
                when (result) {
                    is NetworkStateResource.Success -> {
                        val fullList = result.data
                        currentPage += 1
                        val newChunkSize = fullList.size - prevSize
                        _state.update {
                            GamesListState.Success(
                                games = fullList,
                                isLoadingMore = false,
                                hasMore = newChunkSize >= Constants.PAGE_SIZE
                            )
                        }
                    }
                    is NetworkStateResource.Error,
                    NetworkStateResource.Empty -> {
                        _state.update {
                            (it as? GamesListState.Success)?.copy(isLoadingMore = false) ?: it
                        }
                    }
                    is NetworkStateResource.Loading -> { GamesListState.Loading }
                }
            }
        }
    }
}
