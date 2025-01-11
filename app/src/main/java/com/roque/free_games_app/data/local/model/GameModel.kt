package com.roque.free_games_app.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Games")
data class GameModel(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,

    @ColumnInfo(name = "shortDescription")
    val shortDescription: String,

    @ColumnInfo(name = "gameUrl")
    val gameUrl: String,

    @ColumnInfo(name = "genre")
    val genre: String,

    @ColumnInfo(name = "platform")
    val platform: String,

    @ColumnInfo(name = "publisher")
    val publisher: String,

    @ColumnInfo(name = "developer")
    val developer: String,

    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,

    @ColumnInfo(name = "freetogameProfileUrl")
    val freetogameProfileUrl: String
)