package com.roque.free_games_app.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.roque.free_games_app.mocks.getGameModelMock
import com.roque.free_games_app.repository.local.LocalRepository
import com.roque.free_games_app.ui.screens.home.HomeGamesViewModel
import com.roque.free_games_app.util.DEFAULT_GENRE_ITEM
import com.roque.free_games_app.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class HomeGamesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val localRepository = mock(LocalRepository::class.java)
    private val viewModel = HomeGamesViewModel(localRepository)

    @Test
    fun `initial state loads games by default genre`() =
        runTest(testCoroutineRule.getTestDispatcher()) {
            // Arrange
            val defaultGames = listOf(getGameModelMock())
            `when`(localRepository.getGames()).thenReturn(flow { emit(defaultGames) })

            // Act
            viewModel.getGamesByGenre(DEFAULT_GENRE_ITEM)
            advanceUntilIdle()

            // Assert
            assertEquals(defaultGames, viewModel.games.value)
        }

    @Test
    fun `getGamesByGenre with specific genre updates games`() =
        runTest(testCoroutineRule.getTestDispatcher()) {
            // Arrange
            val genre = "test"
            val genreGames = listOf(getGameModelMock())
            `when`(localRepository.getGamesByGenre(genre)).thenReturn(flow { emit(genreGames) })

            // Act
            viewModel.getGamesByGenre(genre)
            advanceUntilIdle()

            // Assert
            assertEquals(genreGames, viewModel.games.value)
        }

    @Test
    fun `getGamesByGenre with default genre loads all games`() =
        runTest(testCoroutineRule.getTestDispatcher()) {
            // Arrange
            val allGames = listOf(getGameModelMock())
            `when`(localRepository.getGames()).thenReturn(flow { emit(allGames) })

            // Act
            viewModel.getGamesByGenre(DEFAULT_GENRE_ITEM)
            advanceUntilIdle()

            // Assert
            assertEquals(allGames, viewModel.games.value)
        }
}
