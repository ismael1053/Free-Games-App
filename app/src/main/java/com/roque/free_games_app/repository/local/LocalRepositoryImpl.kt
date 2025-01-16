package com.roque.free_games_app.repository.local

import com.roque.free_games_app.data.local.dao.GamesDao
import com.roque.free_games_app.data.local.model.GameModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class LocalRepositoryImpl(
    private val dao: GamesDao,
) : LocalRepository {

    override fun getGames(): Flow<List<GameModel>> = dao.getGames().transform {
        emit(it)
    }

    override suspend fun getGamesById(id: Int?): GameModel = dao.getGamesById(id)

    override fun getGamesByGenre(genre: String): Flow<List<GameModel>> =
        dao.getGamesByGenre(genre).transform {
            emit(it)
        }

    override fun getGenre(): Flow<List<String>> = dao.getGenre().transform {
        emit(it)
    }

    override fun searchGame(titleGame: String): Flow<List<GameModel>> =
        dao.searchGame("%$titleGame%")

    override suspend fun insertGames(games: List<GameModel>): Boolean {
        val insertedIds = dao.insertGames(games)
        return insertedIds.size == games.size
    }

    override suspend fun deleteGame(id: Int?) {
        dao.deleteGame(id)
    }
}