package com.juanmi.gamertool.dependencies

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.juanmi.gamertool.application.api.ApiClient
import com.juanmi.gamertool.repository.auth.AuthRepository
import com.juanmi.gamertool.repository.auth.AuthRepositoryImpl
import com.juanmi.gamertool.repository.firestore.FirestoreRepository
import com.juanmi.gamertool.repository.firestore.FirestoreRepositoryImpl
import com.juanmi.gamertool.repository.retrofit.GameRepository
import com.juanmi.gamertool.repository.retrofit.impl.GameRepositoryImpl
import com.juanmi.gamertool.repository.retrofit.service.GameService
import com.juanmi.gamertool.repository.retrofit.PagingGameRepository
import com.juanmi.gamertool.repository.retrofit.impl.PagingGameRepositoryImpl
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
    fun providesPagingGameRepository(gameRepository: GameRepository) : PagingGameRepository = PagingGameRepositoryImpl(gameRepository)

    @Provides
    @Singleton
    fun providesFirebaseFirestore() : FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    @Singleton
    fun provideFirestoreRepository(firebaseFirestore: FirebaseFirestore): FirestoreRepository = FirestoreRepositoryImpl(firebaseFirestore)

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient =
        ApiClient()

    @Provides
    @Singleton
    fun provideGamesService(apiClient: ApiClient): GameService =
        apiClient.createService(GameService::class.java)

}