package com.example.game_your_game.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.genres.GenresState.*
import com.example.game_your_game.genres.domain.model.Genre
import com.example.game_your_game.genres.domain.usecase.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface GenresState {
    data object Loading : GenresState
    data class Success(val genres: List<Genre>) : GenresState
    data class Error(val message: String) : GenresState
    data class Empty(val message: String) : GenresState

}

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
//    private val navController: NavController,
) : ViewModel() {

    private val _state = MutableStateFlow<GenresState>(GenresState.Loading)
    val state: StateFlow<GenresState> = _state.asStateFlow()

    init {
        loadGenres()
    }

    fun setIntent(action: GenresScreenIntent) {
        when (action) {
            GenresScreenIntent.OnRetry -> loadGenres()
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            _state.update { GenresState.Loading }
            getGenresUseCase().collect { result ->
                when (result) {
                    is NetworkStateResource.Success -> {
                        _state.update { Success(result.data) }
                    }

                    is NetworkStateResource.Error -> {
                        _state.update { Error(result.throwable.message ?: "Unknown error") } }

                    NetworkStateResource.Loading -> {
                        _state.update { GenresState.Loading }
                    }
                }
            }
        }
    }


}
