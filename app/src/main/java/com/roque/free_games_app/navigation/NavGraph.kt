package com.roque.free_games_app.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.roque.free_games_app.R
import com.roque.free_games_app.ui.screens.detail.GameDetail
import com.roque.free_games_app.ui.screens.home.HomeGames

@Composable
fun Navigation(
    navController: NavHostController,
    genres: ArrayList<String>? = null,
) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeGames(
                navController = navController,
                genres
            )
        }
        composable(
            Screen.Detail.route.plus(Screen.Detail.objectPath),
            arguments = listOf(navArgument(Screen.Detail.objectName) {
                type = NavType.IntType
            })
        ) {
            val gameId = it.arguments?.getInt(Screen.Detail.objectName)
            GameDetail(gameId)
        }
    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        Screen.Home.route -> stringResource(id = R.string.app_name)
        Screen.Detail.route -> stringResource(id = R.string.game_detail_title)
        else -> {
            stringResource(R.string.app_name)
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}
