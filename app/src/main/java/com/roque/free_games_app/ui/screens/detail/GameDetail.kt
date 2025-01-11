package com.roque.free_games_app.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.roque.free_games_app.R

@Composable
fun GameDetail(gameId: Int?) {
    val viewModel = hiltViewModel<GameDetailViewModel>()
    val game by viewModel.game.collectAsState()

    SideEffect {
        viewModel.getGame(gameId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        GameHeader(title = game?.title.orEmpty(), thumbnailUrl = game?.thumbnail.orEmpty())

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = game?.shortDescription.orEmpty(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        GameInfo(
            genre = game?.genre.orEmpty(),
            platform = game?.platform.orEmpty(),
            publisher = game?.publisher.orEmpty(),
            developer = game?.developer.orEmpty(),
            releaseDate = game?.releaseDate.orEmpty()
        )
    }
}

@Composable
fun GameHeader(title: String, thumbnailUrl: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(thumbnailUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GameInfo(
    genre: String,
    platform: String,
    publisher: String,
    developer: String,
    releaseDate: String
) {
    Column {
        GameInfoRow(stringResource(R.string.detail_genro), genre)
        GameInfoRow(stringResource(R.string.detail_platform), platform)
        GameInfoRow(stringResource(R.string.detailt_publisher), publisher)
        GameInfoRow(stringResource(R.string.detailt_developer), developer)
        GameInfoRow(stringResource(R.string.detailt_release_date), releaseDate)
    }
}

@Composable
fun GameInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}