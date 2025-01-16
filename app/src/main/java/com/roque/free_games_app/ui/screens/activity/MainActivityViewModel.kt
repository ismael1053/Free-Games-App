package com.roque.free_games_app.ui.screens.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roque.free_games_app.data.local.model.GameModel
import com.roque.free_games_app.repository.local.LocalRepository
import com.roque.free_games_app.repository.remote.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val remoteRepo: RemoteRepository,
    private val localRepo: LocalRepository
) : ViewModel() {
    private val _isLoadingState = MutableStateFlow(true)
    val isLoadingState get() = _isLoadingState.asStateFlow()

    private val _errorState = MutableStateFlow("")
    val errorState: StateFlow<String> get() = _errorState

    fun fetchGameList() {
        viewModelScope.launch {
            remoteRepo.getGames()
                .catch { showError(it) }
                .collect { handleFreeGamesResponse(it) }
        }
    }

    private fun handleFreeGamesResponse(games: List<GameModel>) {
        insertGames(games)
    }

    private fun insertGames(games: List<GameModel>) {
        viewModelScope.launch {
            try {
                _isLoadingState.value = localRepo.insertGames(games).not()
            } catch (e: Exception) {
                _errorState.value = e.localizedMessage.orEmpty()
            }
        }
    }

    private fun showError(throwable: Throwable) {
        Log.e(ERROR_TAG, throwable.message.orEmpty(), throwable)
        _errorState.value = ""
        _isLoadingState.value = false
    }

    fun clearError() {
        _errorState.value = ""
    }

    companion object {
        private const val ERROR_TAG = "MainActivityViewModel"
    }
}