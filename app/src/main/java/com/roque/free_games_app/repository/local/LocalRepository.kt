package com.roque.free_games_app.repository.local

import com.roque.free_games_app.data.local.model.GameModel
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    fun getGames(): Flow<List<GameModel>>

    suspend fun getGamesById(id: Int?): GameModel

    fun getGamesByGenre(genre: String): Flow<List<GameModel>>

    fun getGenre(): Flow<List<String>>

    fun searchGame(titleGame: String): Flow<List<GameModel>>

    suspend fun insertGames(games: List<GameModel>)

    suspend fun deleteGame(id: Int?)
}