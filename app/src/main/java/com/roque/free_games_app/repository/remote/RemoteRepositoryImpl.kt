package com.roque.free_games_app.repository.remote

import com.roque.free_games_app.data.local.model.GameModel
import com.roque.free_games_app.data.mapper.fromGameDTOToModel
import com.roque.free_games_app.data.remote.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteRepositoryImpl(
    private val apiService: ApiService
) : RemoteRepository {

    override fun getGames(): Flow<List<GameModel>> = flow {
        val response = apiService.getGames()
        emit(fromGameDTOToModel(response))
    }
}