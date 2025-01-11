package com.roque.free_games_app.repository

import com.roque.free_games_app.data.local.dao.GamesDao
import com.roque.free_games_app.data.local.model.GameModel
import com.roque.free_games_app.mocks.getGameModelMock
import com.roque.free_games_app.repository.local.LocalRepositoryImpl
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class LocalRepositoryTest {

    @Mock
    private lateinit var dao: GamesDao

    private lateinit var localRepository: LocalRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        localRepository = LocalRepositoryImpl(dao)
    }

    @Test
    fun `getGames should return a list of games from DAO`(): Unit = runTest {
        // Arrange
        val games = listOf(
            getGameModelMock()
        )
        `when`(dao.getGames()).thenReturn(flowOf(games))

        // Act
        val result = localRepository.getGames()

        // Assert
        result.collect { list ->
            assertEquals(games, list)
        }
        verify(dao).getGames()
    }

    @Test
    fun `getGamesById should return a game by ID from DAO`(): Unit = runTest {
        // Arrange
        val game = getGameModelMock()

        `when`(dao.getGamesById(1)).thenReturn(game)

        // Act
        val result = localRepository.getGamesById(1)

        // Assert
        assertEquals(game, result)
        verify(dao).getGamesById(1)
    }

    @Test
    fun `getGamesByGenre should return games filtered by genre from DAO`(): Unit = runTest {
        // Arrange
        val genre = "Action"
        val games = listOf(
            getGameModelMock()
        )
        `when`(dao.getGamesByGenre(genre)).thenReturn(flowOf(games))

        // Act
        val result = localRepository.getGamesByGenre(genre)

        // Assert
        result.collect { list ->
            assertEquals(games, list)
        }
        verify(dao).getGamesByGenre(genre)
    }

    @Test
    fun `insertGames should insert games into DAO`(): Unit = runTest {
        // Arrange
        val games = listOf(
            getGameModelMock()
        )

        // Act
        localRepository.insertGames(games)

        // Assert
        verify(dao).insertGames(games)
    }

    @Test
    fun `deleteGame should delete a game by ID in DAO`(): Unit = runTest {
        // Arrange
        val id = 1

        // Act
        localRepository.deleteGame(id)

        // Assert
        verify(dao).deleteGame(id)
    }
}
