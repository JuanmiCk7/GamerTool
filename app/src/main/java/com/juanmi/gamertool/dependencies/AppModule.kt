package com.juanmi.gamertool.dependencies

import com.juanmi.gamertool.core.network.ApiClient
import com.juanmi.gamertool.repository.retrofit.GameRepositoryImpl
import com.juanmi.gamertool.repository.retrofit.GameService
import com.juanmi.gamertool.repository.retrofit.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGameRepository(
        service: GameService
    ): GameRepository =
        GameRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient =
        ApiClient()

    @Provides
    @Singleton
    fun provideGamesService(apiClient: ApiClient): GameService =
        apiClient.createService(GameService::class.java)

}