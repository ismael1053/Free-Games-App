package com.roque.free_games_app.ui.screens.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.roque.free_games_app.R
import com.roque.free_games_app.navigation.Navigation
import com.roque.free_games_app.navigation.Screen
import com.roque.free_games_app.navigation.currentRoute
import com.roque.free_games_app.navigation.navigationTitle
import com.roque.free_games_app.ui.components.SearchBar
import com.roque.free_games_app.ui.components.SearchUI
import com.roque.free_games_app.ui.components.ShowGenericDialog
import com.roque.free_games_app.ui.theme.FloatingActionBackground
import com.roque.free_games_app.ui.theme.cornerRadius

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()
    val isAppBarVisible = remember { mutableStateOf(true) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val genreList by mainViewModel.genres.collectAsState()
    val games by mainViewModel.games.collectAsState()
    val openDialog = remember { mutableStateOf(false) }
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    LaunchedEffect(Unit) {
        mainViewModel.getGenre()
    }

    Scaffold(topBar = {
        if (!isAppBarVisible.value) {
            SearchBar(isAppBarVisible, mainViewModel)
        } else {
            CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ), title = {
                Text(
                    text = navigationTitle(navController),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
            }, navigationIcon = {
                if (currentRoute(navController) == Screen.Detail.route) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            tint = Color.White
                        )
                    }
                }
            }, scrollBehavior = scrollBehavior, actions = {
                IconButton(onClick = {
                    openDialog.value = true
                }) {
                    if (currentRoute(navController) == Screen.Detail.route) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "delete",
                            tint = Color.White
                        )
                    }
                }
            })
        }
    }, floatingActionButton = {
        if (isAppBarVisible.value && currentRoute(navController) == Screen.Home.route) {
            FloatingActionButton(
                modifier = Modifier.cornerRadius(30),
                containerColor = FloatingActionBackground,
                onClick = {
                    isAppBarVisible.value = false
                },
            ) {
                Icon(Icons.Filled.Search, "", tint = Color.White)
            }
        }
    }) {
        Box(Modifier.padding(it)) {
            Navigation(navController, genreList as ArrayList<String>?)
            if (isAppBarVisible.value.not()) {
                SearchUI(navController, games) {
                    isAppBarVisible.value = true
                }
            }
            if (openDialog.value) {
                ShowGenericDialog(
                    title = stringResource(R.string.game_delate_title),
                    description = stringResource(R.string.game_delate_description),
                    onConfirm = {
                        val gameId =
                            currentBackStackEntry?.arguments?.getInt(Screen.Detail.objectName)
                        mainViewModel.deleteGame(gameId)
                        openDialog.value = false
                        navController.popBackStack()
                    },
                    openDialog
                )
            }
        }
    }
}