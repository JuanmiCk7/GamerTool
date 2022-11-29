package com.juanmi.gamertool.repository.firestore

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.juanmi.gamertool.repository.model.Game

/***
 * Interfaz que define los métodos utilizados para la gestión de juegos en Firestore.
 */
interface FirestoreRepository {
    fun saveGame(game: Game, context: Context, currentUser: FirebaseUser)
    fun setState(game: Game, state: Boolean, context: Context, currentUser: FirebaseUser)
    fun deleteGame(game: Game, context: Context, currentUser: FirebaseUser)
    suspend fun getAllGames(currentUser: FirebaseUser): List<Game>
}