package com.roque.free_games_app.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roque.free_games_app.ui.components.Games
import com.roque.free_games_app.util.DEFAULT_GENRE_ITEM

@Composable
fun HomeGames(
    navController: NavController,
    genres: ArrayList<String>? = null,
) {
    val viewModel = hiltViewModel<HomeGamesViewModel>()
    val games by viewModel.games.collectAsState()

    val genre = remember { mutableStateOf(DEFAULT_GENRE_ITEM) }

    SideEffect {
        viewModel.getGamesByGenre(genre.value)
    }
    Games(
        navController = navController,
        games = games,
        genres = genres,
        selectedName = viewModel.selectedGenre.value
    ) {
        genre.value = it.toString()
        it?.let {
            viewModel.selectedGenre.value = it
        }
    }
}