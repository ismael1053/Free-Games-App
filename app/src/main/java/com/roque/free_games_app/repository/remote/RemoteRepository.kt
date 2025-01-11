package com.roque.free_games_app.repository.remote

import com.roque.free_games_app.data.local.model.GameModel
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    fun getGames(): Flow<List<GameModel>>
}