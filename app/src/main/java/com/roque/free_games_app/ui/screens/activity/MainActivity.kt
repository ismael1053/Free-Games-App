package com.roque.free_games_app.ui.screens.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.roque.free_games_app.R
import com.roque.free_games_app.ui.screens.mainscreen.MainScreen
import com.roque.free_games_app.ui.theme.FreeGamesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchGameList()
        installSplashScreen().apply {
            setKeepOnScreenCondition { viewModel.isLoadingState.value }
        }
        setContent {
            val isLoading = viewModel.isLoadingState.collectAsState()
            FreeGamesAppTheme {
                val errorMessage by viewModel.errorState.collectAsState()
                if (errorMessage.isNotEmpty()) {
                    // Show Error
                    Snackbar(
                        action = {
                            Button(onClick = { viewModel.clearError() }) {
                                Text(stringResource(R.string.ok))
                            }
                        }
                    ) {
                        Text(errorMessage)
                    }
                }

                // Display loading indicator until data is fetched
                if (isLoading.value) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    MainScreen()
                }
            }
        }
    }
}