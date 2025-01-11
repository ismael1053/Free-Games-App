package com.roque.free_games_app.ui.screens.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roque.free_games_app.data.local.model.GameModel
import com.roque.free_games_app.repository.local.LocalRepository
import com.roque.free_games_app.util.DEFAULT_GENRE_ITEM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {
    private val _games = MutableStateFlow<List<GameModel>>(arrayListOf())
    val games get() = _games.asStateFlow()

    private val _genres = MutableStateFlow<List<String>>(arrayListOf())
    val genres get() = _genres.asStateFlow()

    fun getGenre() {
        viewModelScope.launch {
            localRepository.getGenre().onEach {
                val genreList: MutableList<String> = mutableListOf()
                genreList.addAll(it)
                if (genreList.first() != DEFAULT_GENRE_ITEM) {
                    genreList.add(0, DEFAULT_GENRE_ITEM)
                }
                _genres.value = genreList
            }.launchIn(viewModelScope)
        }
    }

    fun deleteGame(gameId: Int?) {
        viewModelScope.launch {
            localRepository.deleteGame(gameId)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @FlowPreview
    fun searchGame(searchKey: String) {
        viewModelScope.launch {
            flowOf(searchKey).debounce(300)
                .filter {
                    it.trim().isEmpty().not()
                }
                .distinctUntilChanged()
                .flatMapLatest {
                    localRepository.searchGame(it)
                }.collect {
                    _games.value = it
                }
        }
    }
}