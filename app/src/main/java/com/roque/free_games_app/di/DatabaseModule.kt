package com.roque.free_games_app.di

import android.app.Application
import androidx.room.Room
import com.roque.free_games_app.data.local.AppDatabase
import com.roque.free_games_app.data.local.dao.GamesDao
import com.roque.free_games_app.util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): AppDatabase =
        Room
            .databaseBuilder(
                app,
                AppDatabase::class.java,
                DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideBookmarkDao(db: AppDatabase): GamesDao =
        db.bookmarkDao()
}