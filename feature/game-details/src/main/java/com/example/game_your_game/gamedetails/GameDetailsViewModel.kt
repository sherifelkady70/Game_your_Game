package com.example.game_your_game.gamedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.gamedetails.domain.model.GameDetails
import com.example.game_your_game.gamedetails.domain.usecase.GetGameDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface GameDetailsState {
    data object Loading : GameDetailsState
    data class Success(val details: GameDetails) : GameDetailsState
    data class Error(val message: String) : GameDetailsState
}

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGameDetailsUseCase: GetGameDetailsUseCase
) : ViewModel() {

    private val gameId: Int = checkNotNull(savedStateHandle["gameId"]) { "gameId required" }

    private val _state = MutableStateFlow<GameDetailsState>(GameDetailsState.Loading)
    val state: StateFlow<GameDetailsState> = _state.asStateFlow()

    init {
        loadDetails()
    }

    fun loadDetails() {
        viewModelScope.launch {
            _state.update { GameDetailsState.Loading }
            getGameDetailsUseCase(gameId).collect { result ->
                when(result){
                    is NetworkStateResource.Success -> {
                        _state.update { GameDetailsState.Success(result.data) }
                    }
                    is NetworkStateResource.Error -> {
                        _state.update { GameDetailsState.Error(result.throwable.message ?: "Unknown error") }
                    }
                    is NetworkStateResource.Loading -> {
                        _state.update { GameDetailsState.Loading }
                    }
                }
            }
        }
    }
}
