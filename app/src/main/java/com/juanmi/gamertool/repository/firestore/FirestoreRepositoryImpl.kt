package com.juanmi.gamertool.repository.firestore

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.juanmi.gamertool.R
import com.juanmi.gamertool.model.Game
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/***
 * Clase que implementa los métodos utilizados para la gestión de juegos en Firestore.
 */
class FirestoreRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
    ) : FirestoreRepository {

    private val CURRENT_PAGE_SIZE = 50L

    override fun saveGame(game: Game, context: Context, currentUser: FirebaseUser) {
        val data = hashMapOf(
            "id" to game.id,
            "cover" to game.cover,
            "genres" to game.genres,
            "name" to game.name,
            "platforms" to game.platforms,
            "storyline" to game.storyline,
            "summary" to game.summary,
            "url" to game.url,
            "releaseDate" to game.releaseDate,
            "follows" to game.follows,
            "involvedCompanies" to game.involvedCompanies,
            "rating" to game.rating,
            "totalRating" to game.rating,
            "ratingCount" to game.ratingCount,
            "screenshots" to game.screenshots,
            "complete" to game.complete,
            "comesFromFirestore" to true
        )

        firebaseFirestore.collection(currentUser.email!!).document(game.id.toString()).set(data)
            .addOnSuccessListener {
                Toast.makeText(context, R.string.toast_save_game, Toast.LENGTH_SHORT).show()
            }
            .addOnCanceledListener {
                Toast.makeText(context, R.string.toast_save_game_failed, Toast.LENGTH_SHORT).show()
            }
    }

    override fun setState(game: Game, state: Boolean, context: Context, currentUser: FirebaseUser) {
        firebaseFirestore.collection(currentUser.email!!).document(game.id.toString()).update("complete", state)
            .addOnSuccessListener {
                Toast.makeText(context, R.string.toast_success_set_completed, Toast.LENGTH_SHORT).show()
            }
            .addOnCanceledListener {
                Toast.makeText(context, R.string.toast_failed_set_completed, Toast.LENGTH_SHORT).show()
            }
    }

    override fun deleteGame(game: Game, context: Context, currentUser: FirebaseUser) {
        firebaseFirestore.collection(currentUser.email!!).document(game.id.toString()).delete()
            .addOnSuccessListener {
                Toast.makeText(context, R.string.toast_game_deleted, Toast.LENGTH_SHORT).show()
            }
            .addOnCanceledListener {
                Toast.makeText(context, R.string.toast_game_deleted_fail, Toast.LENGTH_SHORT).show()
            }
    }

    override suspend fun getAllGames(currentUser: FirebaseUser): Query {
        return firebaseFirestore.collection(currentUser.email!!).limit(CURRENT_PAGE_SIZE)
    }
}