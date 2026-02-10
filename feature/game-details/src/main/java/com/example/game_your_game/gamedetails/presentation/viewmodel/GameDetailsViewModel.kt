package com.example.game_your_game.gamedetails.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.game_your_game.core.utilits.Constants
import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.gamedetails.domain.usecase.GetGameDetailsUseCase
import com.example.game_your_game.gamedetails.presentation.intent.GameDetailsScreenIntent
import com.example.game_your_game.gamedetails.presentation.intent.GameDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGameDetailsUseCase: GetGameDetailsUseCase
) : ViewModel() {

    private val gameId: Int = checkNotNull(savedStateHandle[Constants.GAME_ID]) { Constants.GAME_ID_REQUIRED }

    private val _state = MutableStateFlow<GameDetailsState>(GameDetailsState.Loading)
    val state: StateFlow<GameDetailsState> = _state.asStateFlow()

    init {
        loadDetails()
    }

    fun setIntent(action: GameDetailsScreenIntent) {
        when (action) {
            GameDetailsScreenIntent.OnRetry -> loadDetails()
        }
    }

    private fun loadDetails() {
        viewModelScope.launch {
            _state.update { GameDetailsState.Loading }
            getGameDetailsUseCase(gameId).collect { result ->
                when (result) {
                    is NetworkStateResource.Success -> {
                        _state.update { GameDetailsState.Success(result.data) }
                    }
                    is NetworkStateResource.Error -> {
                        _state.update { GameDetailsState.Error(result.throwable.message ?: Constants.GENERAL_ERROR_MESSAGE) }
                    }
                    NetworkStateResource.Empty -> {
                        _state.update { GameDetailsState.Empty(Constants.EMPTY_MESSAGE) }
                    }
                    is NetworkStateResource.Loading -> {
                        _state.update { GameDetailsState.Loading }
                    }
                }
            }
        }
    }
}
