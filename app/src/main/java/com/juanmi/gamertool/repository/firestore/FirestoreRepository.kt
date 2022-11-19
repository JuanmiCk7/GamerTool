package com.juanmi.gamertool.repository.firestore

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.juanmi.gamertool.repository.model.Game

interface FirestoreRepository {

    fun saveGame(game: Game, context: Context, currentUser: FirebaseUser)

    fun setCompleted(game: Game, context: Context, currentUser: FirebaseUser)

    fun deleteGame(game: Game, context: Context, currentUser: FirebaseUser)

    suspend fun getAllGames(currentUser: FirebaseUser): List<Game>
}