package com.roque.free_games_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roque.free_games_app.data.local.dao.GamesDao
import com.roque.free_games_app.data.local.model.GameModel

@Database(
    entities = [GameModel::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): GamesDao
}