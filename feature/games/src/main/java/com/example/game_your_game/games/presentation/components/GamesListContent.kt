package com.example.game_your_game.games.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.game_your_game.core.theme.GameYourGameTheme
import com.example.game_your_game.core.theme.TextSecondary
import com.example.game_your_game.games.domain.model.Game
import com.example.game_your_game.games.presentation.intent.GamesListState

private const val ERROR_MESSAGE = "Something wrong ... try again"
@Composable
fun GamesListContent(
    modifier: Modifier = Modifier,
    state: GamesListState,
    onGameClick: (Game) -> Unit,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is GamesListState.Loading -> CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            is GamesListState.Success -> {
                if (state.games.isEmpty()) {
                    Text(
                        text = "No games found",//
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                } else {//
                    val listState = rememberLazyListState()
                    val shouldLoadMore by remember {
                        derivedStateOf {
                            val layoutInfo = listState.layoutInfo
                            val totalItems = layoutInfo.totalItemsCount
                            val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                            totalItems > 0 && lastVisibleIndex >= totalItems - 2
                        }
                    }
                    LaunchedEffect(shouldLoadMore, state.isLoadingMore, state.hasMore) {
                        if (shouldLoadMore && state.hasMore && !state.isLoadingMore) {
                            onLoadMore()
                        }
                    }
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        items(state.games, key = { it.id }) { game ->
                            GameListItem(
                                game = game,
                                onClick = { onGameClick(game) }
                            )
                        }
                        if (state.isLoadingMore) {
                            item(key = "loading_more") {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is GamesListState.Error -> ErrorWithRetry(
                message = ERROR_MESSAGE,
                onRetry = onRetry,
                modifier = Modifier.padding(16.dp)
            )
            is GamesListState.Empty -> ErrorWithRetry(
                message = state.message,
                onRetry = onRetry,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun ErrorWithRetry(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
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
            Text("Retry")
        }
    }
}
@Suppress("Preview")
@Preview(showBackground = true)
@Composable
private fun ErrorWithRetryPreview() {
    GameYourGameTheme {
        ErrorWithRetry(
            message = "Something went wrong",
            onRetry = {}
        )
    }
}