package com.roque.free_games_app.navigation

import androidx.annotation.StringRes
import com.roque.free_games_app.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int = R.string.app_name,
    val objectName: String = "",
    val objectPath: String = ""
) {
    data object Home : Screen("home_games")
    data object Detail : Screen("detail_game", objectName = "game", objectPath = "/{game}")
}