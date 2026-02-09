package com.example.game_your_game.games.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.game_your_game.games.domain.model.Game
import com.example.game_your_game.core.theme.RatingHighlight
import com.example.game_your_game.core.theme.SurfaceCard
import com.example.game_your_game.core.theme.TextPrimary

@Composable
fun GameListItem(
    game: Game,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .background(SurfaceCard, shape = MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = game.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(72.dp)
                .height(72.dp),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = game.name,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                maxLines = 2
            )
            game.rating?.let { rating ->
                Text(
                    text = String.format("%.1f", rating),
                    style = MaterialTheme.typography.labelMedium,
                    color = RatingHighlight
                )
            }
        }
    }
}
