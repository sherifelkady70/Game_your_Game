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
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface GamesListState {
    data object Loading : GamesListState
    data class Success(val games: List<Game>) : GamesListState
    data class Error(val message: String) : GamesListState
}

@HiltViewModel
class GamesListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGamesByGenreUseCase: GetGamesByGenreUseCase
) : ViewModel() {

    private val genreId: Int = checkNotNull(savedStateHandle["genreId"]) { "genreId required" }

    private val _state = MutableStateFlow<GamesListState>(GamesListState.Loading)
    val state: StateFlow<GamesListState> = _state.asStateFlow()

    init {
        loadGames()
    }

    fun loadGames() {
        viewModelScope.launch {
            _state.update { GamesListState.Loading }
            getGamesByGenreUseCase(genreId).collect { result ->
                when(result){
                    is NetworkStateResource.Success<*> -> {
                        _state.update { GamesListState.Success(result.data as List<Game>) }
                    }
                    is NetworkStateResource.Error -> {
                        _state.update { GamesListState.Error(result.throwable.message ?: "UnKnown Error") }
                    }
                    is NetworkStateResource.Loading -> {
                        _state.update { GamesListState.Loading }
                    }
                }
            }
        }
    }
}
