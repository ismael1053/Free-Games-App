package com.roque.free_games_app.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import com.roque.free_games_app.data.remote.dto.DetailedGameDTO
import com.roque.free_games_app.data.remote.dto.GameDTO

interface ApiService {

    @GET("games")
    suspend fun getGames(): List<GameDTO>

    @GET("game")
    suspend fun getGameDetails(
        @Query("id") id: Int,
    ): DetailedGameDTO
}