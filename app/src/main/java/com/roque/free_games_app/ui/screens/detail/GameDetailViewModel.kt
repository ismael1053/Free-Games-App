package com.roque.free_games_app.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roque.free_games_app.data.local.model.GameModel
import com.roque.free_games_app.repository.local.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(private val localRepository: LocalRepository) :
    ViewModel() {

    private val _game = MutableStateFlow<GameModel?>(null)
    val game get() = _game.asStateFlow()

    fun getGame(id: Int?) {
        viewModelScope.launch {
            _game.value = localRepository.getGamesById(id)
        }
    }
}