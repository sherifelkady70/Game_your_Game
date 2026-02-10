package com.example.game_your_game.games

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.games.domain.model.Game
import com.example.game_your_game.games.domain.usecase.GetGamesByGenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGE_SIZE = 10

sealed interface GamesListState {
    data object Loading : GamesListState
    data class Success(
        val games: List<Game>,
        val isLoadingMore: Boolean = false,
        val hasMore: Boolean = true
    ) : GamesListState
    data class Error(val message: String) : GamesListState
}

@HiltViewModel
class GamesListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGamesByGenreUseCase: GetGamesByGenreUseCase
) : ViewModel() {

    private val genreId: Int = checkNotNull(savedStateHandle["genreId"]) { "genreId required" }
    private var currentPage = 1

    private val _state = MutableStateFlow<GamesListState>(GamesListState.Loading)
    val state: StateFlow<GamesListState> = _state.asStateFlow()

    init {
        loadGames()
    }

    fun setIntent(action : GamesScreenIntent){
        when(action){
            is GamesScreenIntent.OnLoadMore -> loadMoreGames()
        }
    }
    fun loadGames() {
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
                                hasMore = list.size >= PAGE_SIZE
                            )
                        }
                    }
                    is NetworkStateResource.Error -> {
                        _state.update {
                            GamesListState.Error(result.throwable.message ?: "Unknown Error")
                        }
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
            val prevSize = (current as GamesListState.Success).games.size
            _state.update {
                (it as? GamesListState.Success)?.copy(isLoadingMore = true) ?: it
            }
            getGamesByGenreUseCase(genreId, currentPage + 1).collect { result ->
                when (result) {
                    is NetworkStateResource.Success -> {
                        val fullList = result.data as List<Game>
                        currentPage += 1
                        val newChunkSize = fullList.size - prevSize
                        _state.update {
                            GamesListState.Success(
                                games = fullList,
                                isLoadingMore = false,
                                hasMore = newChunkSize >= PAGE_SIZE
                            )
                        }
                    }
                    is NetworkStateResource.Error -> {
                        _state.update {
                            (it as? GamesListState.Success)?.copy(isLoadingMore = false) ?: it
                        }
                    }
                    is NetworkStateResource.Loading -> { /* keep isLoadingMore true */ }
                }
            }
        }
    }
}
