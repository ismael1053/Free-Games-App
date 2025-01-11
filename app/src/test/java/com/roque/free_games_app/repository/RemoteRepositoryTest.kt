package com.roque.free_games_app.repository

import app.cash.turbine.test
import com.roque.free_games_app.data.mapper.fromGameDTOToModel
import com.roque.free_games_app.data.remote.api.ApiService
import com.roque.free_games_app.mocks.getGameDTOMock
import com.roque.free_games_app.repository.remote.RemoteRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.junit.Test

class RemoteRepositoryTest {
    private lateinit var apiService: ApiService
    private lateinit var remoteRepository: RemoteRepositoryImpl

    @Before
    fun setUp() {
        apiService = mock()
        remoteRepository = RemoteRepositoryImpl(apiService)
    }

    @Test
    fun `getGames emits mapped game models`(): Unit = runBlocking {
        // Arrange
        val gameDTOs = listOf(
            getGameDTOMock()
        )
        val expectedGameModels = fromGameDTOToModel(gameDTOs)

        `when`(apiService.getGames()).thenReturn(gameDTOs)

        // Act & Assert
        remoteRepository.getGames().test {
            val result = awaitItem()
            assertEquals(expectedGameModels, result)
            cancelAndIgnoreRemainingEvents()
        }

        // Verify
        verify(apiService).getGames()
    }
}