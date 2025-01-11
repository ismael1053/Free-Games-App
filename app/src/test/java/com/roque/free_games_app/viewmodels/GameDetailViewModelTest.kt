package com.roque.free_games_app.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.roque.free_games_app.mocks.getGameModelMock
import com.roque.free_games_app.repository.local.LocalRepository
import com.roque.free_games_app.ui.screens.detail.GameDetailViewModel
import com.roque.free_games_app.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class GameDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val localRepository = mock(LocalRepository::class.java)
    private val viewModel = GameDetailViewModel(localRepository)

    @Test
    fun `getGame updates game state with valid ID`() = runTest(testCoroutineRule.getTestDispatcher()) {
        // Arrange
        val gameId = 1
        val expectedGame = getGameModelMock()
        `when`(localRepository.getGamesById(gameId)).thenReturn(expectedGame)

        // Act
        viewModel.getGame(gameId)
        advanceUntilIdle()

        // Assert
        assertEquals(expectedGame, viewModel.game.value)
    }

    @Test
    fun `getGame sets game state to null for invalid ID`() = runTest(testCoroutineRule.getTestDispatcher()) {
        // Arrange
        val gameId = null
        `when`(localRepository.getGamesById(gameId)).thenReturn(null)

        // Act
        viewModel.getGame(gameId)
        advanceUntilIdle()

        // Assert
        assertEquals(null, viewModel.game.value)
    }
}
