package com.example.game_your_game.gamedetails.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.game_your_game.gamedetails.domain.model.GameDetails
import com.example.game_your_game.core.theme.RatingHighlight
import com.example.game_your_game.core.theme.TextPrimary
import com.example.game_your_game.core.theme.TextSecondary
import com.example.game_your_game.gamedetails.presentation.intent.GameDetailsState

private const val ERROR_MESSAGE = "Something wrong ... try again"

@Composable
fun GameDetailsContent(
    state: GameDetailsState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is GameDetailsState.Loading -> CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            is GameDetailsState.Success -> GameDetailsBody(details = state.details)
            is GameDetailsState.Error -> ErrorWithRetry(
                message = ERROR_MESSAGE,
                onRetry = onRetry,
                modifier = Modifier.padding(16.dp)
            )
            is GameDetailsState.Empty -> ErrorWithRetry(
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

@Composable
private fun GameDetailsBody(
    details: GameDetails,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        details.imageUrl?.let { url ->
            AsyncImage(
                model = url,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Text(
            text = details.name,
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(12.dp))
        details.releaseDate?.let { date ->
            LabelValue(label = "Release date", value = date)
            Spacer(modifier = Modifier.height(8.dp))
        }
        details.rating?.let { rating ->
            LabelValue(label = "Rating", value = String.format("%.1f", rating), valueColor = RatingHighlight)
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (!details.description.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Description",
                style = MaterialTheme.typography.labelMedium,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = details.description,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )
        }
    }
}

@Composable
private fun LabelValue(
    label: String,
    value: String,
    valueColor: Color = TextPrimary
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = valueColor
        )
    }
}
