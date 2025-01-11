package com.roque.free_games_app.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.roque.free_games_app.data.local.model.GameModel

@Dao
interface GamesDao {

    @Query("SELECT * FROM Games")
    fun getGames(): Flow<List<GameModel>>

    @Query("SELECT * FROM Games WHERE id = :id")
    suspend fun getGamesById(id: Int?): GameModel

    @Query("SELECT * FROM Games WHERE genre = :genre")
    fun getGamesByGenre(genre: String): Flow<List<GameModel>>

    @Query("SELECT DISTINCT genre FROM Games")
    fun getGenre(): Flow<List<String>>

    @Query("SELECT * FROM Games WHERE title LIKE :title")
    fun searchGame(title: String): Flow<List<GameModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameModel>)

    @Query("DELETE FROM Games WHERE id = :id")
    suspend fun deleteGame(id: Int?)
}