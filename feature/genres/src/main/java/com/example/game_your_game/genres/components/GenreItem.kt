package com.example.game_your_game.genres.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.game_your_game.genres.domain.model.Genre
import com.example.game_your_game.core.theme.SurfaceCard
import com.example.game_your_game.core.theme.TextPrimary

@Composable
fun GenreItem(
    modifier: Modifier = Modifier,
    genre: Genre,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .background(SurfaceCard, shape = MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = genre.name,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary
        )
    }
}
