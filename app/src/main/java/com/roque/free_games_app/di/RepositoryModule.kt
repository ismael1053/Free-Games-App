package com.roque.free_games_app.di

import com.roque.free_games_app.data.local.dao.GamesDao
import com.roque.free_games_app.data.remote.api.ApiService
import com.roque.free_games_app.repository.local.LocalRepository
import com.roque.free_games_app.repository.local.LocalRepositoryImpl
import com.roque.free_games_app.repository.remote.RemoteRepository
import com.roque.free_games_app.repository.remote.RemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLocalRepository(
        dao: GamesDao
    ): LocalRepository = LocalRepositoryImpl(dao)

    @Singleton
    @Provides
    fun provideRemoteRepository(
        api: ApiService
    ): RemoteRepository = RemoteRepositoryImpl(api)
}