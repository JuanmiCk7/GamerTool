package com.juanmi.gamertool.dependencies

import com.google.firebase.auth.FirebaseAuth
import com.juanmi.gamertool.application.api.ApiClient
import com.juanmi.gamertool.repository.auth.AuthRepository
import com.juanmi.gamertool.repository.auth.AuthRepositoryImpl
import com.juanmi.gamertool.repository.firestore.FirestoreRepository
import com.juanmi.gamertool.repository.firestore.FirestoreRepositoryImpl
import com.juanmi.gamertool.repository.retrofit.GameRepository
import com.juanmi.gamertool.repository.retrofit.GameRepositoryImpl
import com.juanmi.gamertool.repository.retrofit.GameService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/***
 * Inyección de dependencias con Dagger Hilt utilizando el patrón singleton.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGameRepository(service: GameService): GameRepository =
        GameRepositoryImpl(service)

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    @Singleton
    fun provideFirestoreRepository(): FirestoreRepository = FirestoreRepositoryImpl()

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient =
        ApiClient()

    @Provides
    @Singleton
    fun provideGamesService(apiClient: ApiClient): GameService =
        apiClient.createService(GameService::class.java)

}