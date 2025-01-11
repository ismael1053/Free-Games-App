package com.roque.free_games_app.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.roque.free_games_app.utils.TestCoroutineRule
import com.roque.free_games_app.mocks.getGameModelMock
import com.roque.free_games_app.repository.local.LocalRepository
import com.roque.free_games_app.ui.screens.mainscreen.MainViewModel
import com.roque.free_games_app.util.DEFAULT_GENRE_ITEM
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val localRepository = mock(LocalRepository::class.java)
    private val viewModel = MainViewModel(localRepository)

    @Test
    fun `getGenre updates genres state`() = runTest(testCoroutineRule.getTestDispatcher()) {
        // Arrange
        val genreList = listOf("Action", "Adventure")
        `when`(localRepository.getGenre()).thenReturn(flow { emit(genreList) })

        // Act
        viewModel.getGenre()
        advanceUntilIdle()

        // Assert
        val expectedList = listOf(DEFAULT_GENRE_ITEM) + genreList
        assertEquals(expectedList, viewModel.genres.value)
    }

    @Test
    fun `deleteGame calls repository deleteGame`() =
        runTest(testCoroutineRule.getTestDispatcher()) {
            // Arrange
            val gameId = 123

            // Act
            viewModel.deleteGame(gameId)
            advanceUntilIdle()

            // Assert
            verify(localRepository).deleteGame(gameId)
        }

    @OptIn(FlowPreview::class)
    @Test
    fun `searchGame updates games state`() = runTest(testCoroutineRule.getTestDispatcher()) {
        // Arrange
        val searchKey = "Game"
        val gameList = listOf(
            getGameModelMock()
        )
        `when`(localRepository.searchGame(searchKey)).thenReturn(flow { emit(gameList) })

        // Act
        viewModel.searchGame(searchKey)
        advanceUntilIdle()

        // Assert
        assertEquals(gameList, viewModel.games.value)
    }
}
