package com.example.game_your_game.genres.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.game_your_game.core.utilits.Constants
import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.genres.presentation.intent.GenresScreenIntent
import com.example.game_your_game.genres.presentation.intent.GenresState
import com.example.game_your_game.genres.domain.usecase.GetGenresUseCase
import com.example.game_your_game.genres.presentation.intent.GenresScreenEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GenresViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
) : ViewModel() {

    private val _genreState = MutableStateFlow<GenresState>(GenresState.Loading)
    val genreState: StateFlow<GenresState> = _genreState.asStateFlow()

    private val _viewEffect: Channel<GenresScreenEffect> = Channel()
    val viewEffect = _viewEffect.receiveAsFlow()


    init {
        loadGenres()
    }

    fun setIntent(action: GenresScreenIntent) {
        when (action) {
            GenresScreenIntent.OnRetry -> loadGenres()
            is GenresScreenIntent.OnGenresClicked -> sendOutput { GenresScreenEffect.NavigateToGameList(action.genreId) }

        }
    }
    private fun sendOutput(action: () -> GenresScreenEffect) {
        viewModelScope.launch {
            _viewEffect.send(action())
        }
    }
    private fun loadGenres() {
        viewModelScope.launch {
            getGenresUseCase().collect { result ->
                when (result) {
                    is NetworkStateResource.Success -> {
                        _genreState.update { GenresState.Success(result.data) }
                    }
                    is NetworkStateResource.Error -> {
                        _genreState.update { GenresState.Error(result.throwable.message ?: Constants.GENERAL_ERROR_MESSAGE) }//
                    }
                    NetworkStateResource.Empty -> {
                        _genreState.update { GenresState.Empty(Constants.EMPTY_MESSAGE) }
                    }
                    NetworkStateResource.Loading -> {
                        _genreState.update { GenresState.Loading }
                    }
                }
            }
        }
    }


}
