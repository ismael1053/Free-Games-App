package com.roque.free_games_app.data.mapper

import com.roque.free_games_app.data.local.model.GameModel
import com.roque.free_games_app.data.remote.dto.GameDTO

fun fromGameDTOToModel(items: List<GameDTO>) =
    items.map { fromGameDTOToModel(it) }

fun fromGameDTOToModel(item: GameDTO) =
    GameModel(
        id = item.id,
        title = item.title,
        thumbnail = item.thumbnail,
        shortDescription = item.shortDescription,
        gameUrl = item.gameUrl,
        genre = item.genre,
        platform = item.platform,
        publisher = item.publisher,
        developer = item.developer,
        releaseDate = item.releaseDate,
        freetogameProfileUrl = item.freetogameProfileUrl
    )