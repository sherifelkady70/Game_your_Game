package com.example.game_your_game.genres.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.game_your_game.core.theme.TextSecondary
import com.example.game_your_game.genres.R
import com.example.game_your_game.genres.domain.model.Genre
import com.example.game_your_game.genres.presentation.intent.GenresState


@Composable
fun GenresListContent(
    modifier: Modifier = Modifier,
    state: GenresState,
    onGenreClick: (Genre) -> Unit,
    onRetry: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is GenresState.Loading -> CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            is GenresState.Success -> {
                if (state.genres.isEmpty()) {
                    Text(
                        text = stringResource( R.string.no_genre_found),
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        items(state.genres, key = { it.id }) { genre ->
                            GenreItem(
                                genre = genre,
                                onClick = { onGenreClick(genre) }
                            )
                        }
                    }
                }
            }
            is GenresState.Error -> ErrorWithRetry(
                message = state.message,
                onRetry = onRetry,
                modifier = Modifier.padding(16.dp)
            )
            is GenresState.Empty -> ErrorWithRetry(
                message = state.message,
                onRetry = onRetry,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun ErrorWithRetry(
    modifier: Modifier = Modifier,
    message: String,
    onRetry: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary
        )
        Button(onClick = onRetry) {
            Text(stringResource( R.string.retry))
        }
    }
}
