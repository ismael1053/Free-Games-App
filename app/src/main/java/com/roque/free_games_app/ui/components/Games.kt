package com.roque.free_games_app.ui.components

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.roque.free_games_app.R
import com.roque.free_games_app.data.local.model.GameModel
import com.roque.free_games_app.navigation.Screen
import com.roque.free_games_app.navigation.currentRoute

@Composable
fun Games(
    navController: NavController,
    games: List<GameModel>,
    genres: List<String>? = null,
    selectedName: String?,
    onClickGenre: (String?) -> Unit,
) {
    val activity = LocalContext.current as? Activity
    val openDialog = remember { mutableStateOf(false) }

    BackHandler(enabled = currentRoute(navController) == Screen.Home.route) {
        openDialog.value = true
    }

    Column {
        genres?.let { DisplayGenres(it, selectedName, onClickGenre) }
        DisplayGames(games, navController)
        if (openDialog.value) {
            ShowGenericDialog(
                title = stringResource(R.string.close_the_app),
                description = stringResource(R.string.do_you_want_to_exit_the_app),
                onConfirm = { activity?.finish() },
                openDialog
            )
        }
    }
}

@Composable
fun DisplayGenres(
    genres: List<String>,
    selectedName: String?,
    onClick: (String?) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        items(genres) { item ->
            SelectableGenreChip(selected = item == selectedName,
                genre = item,
                onclick = { onClick(item) })
        }
    }
}


@Composable
fun DisplayGames(
    games: List<GameModel>,
    navController: NavController
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(games) { game ->
            GameCard(
                navController,
                game
            )
        }
    }
}

@Composable
fun GameCard(
    navController: NavController,
    game: GameModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(
                    Screen.Detail.route.plus(
                        "/${game.id}"
                    )
                )
            },
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(game.thumbnail),
                contentDescription = game.title,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray)
                    .aspectRatio(16 / 9f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = game.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = game.shortDescription, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}