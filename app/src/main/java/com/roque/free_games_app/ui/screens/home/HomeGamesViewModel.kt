package com.roque.free_games_app.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roque.free_games_app.data.local.model.GameModel
import com.roque.free_games_app.repository.local.LocalRepository
import com.roque.free_games_app.util.DEFAULT_GENRE_ITEM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeGamesViewModel @Inject constructor(private val localRepository: LocalRepository) :
    ViewModel() {

    var selectedGenre: MutableState<String> = mutableStateOf(DEFAULT_GENRE_ITEM)

    private val _games = MutableStateFlow<List<GameModel>>(arrayListOf())
    val games get() = _games.asStateFlow()

    fun getGamesByGenre(genre: String) {
        viewModelScope.launch {
            if (genre != DEFAULT_GENRE_ITEM) {
                localRepository.getGamesByGenre(genre).onEach {
                    _games.value = it
                }.launchIn(viewModelScope)
            } else {
                localRepository.getGames().onEach {
                    _games.value = it
                }.launchIn(viewModelScope)
            }
        }
    }
}

