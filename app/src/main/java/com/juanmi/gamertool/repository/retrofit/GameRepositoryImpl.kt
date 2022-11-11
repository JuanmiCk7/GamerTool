package com.juanmi.gamertool.repository.retrofit

import android.util.Log
import com.juanmi.gamertool.core.network.ResultData
import com.juanmi.gamertool.core.safeCall
import com.juanmi.gamertool.repository.model.Game
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementación de la interfaz GameRepository
 */
class GameRepositoryImpl @Inject constructor(
    private val service: GameService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GameRepository {

    /**
     * Método que devuelve un ArrayList de juegos obtenidos de la llamada a la API.
     *
     * params{currentPage: Página actual en la que está buscando}
     */
    override suspend fun getGames(currentPage: Int): ResultData<ArrayList<Game>?> {
        Log.d("Hey", "The code gets to getGames method")
        return withContext(dispatcher) {
            return@withContext safeCall {
                service.getGames(
                    getGamesQuery(currentPage))
            }
        }
    }

    /**
     * Método que devuelve un ArrayList de juegos obtenidos por nombre de la llamada a la API.
     *
     * params{name: Nombre del juego que se está buscando}
     */
    override suspend fun getGamesByName(name: String): ResultData<ArrayList<Game>?> {
        Log.d("Hey", "The code gets to getGamesByName method")
        return withContext(dispatcher) {
            return@withContext safeCall {
                service.getGamesByName(
                    name ,getGamesByNameQuery())
            }
        }
    }

    companion object {


        const val GAME_FETCH_NUMBER = 50

        /**
         * Calcula el offset para la query
         */
        private fun calculateOffset(currentPage: Int): Int {
            return (currentPage - 1) * GAME_FETCH_NUMBER
        }

        /**
         * Devuelve la query con los campos que se van a buscar
         */
        fun getGamesByNameQuery() : String {
            return "id, name, first_release_date,summary, storyline, cover.url, platforms.name," +
                    "platforms.platform_logo.url, category, genres.name,rating, rating_count, total_rating," +
                    "total_rating_count,url,screenshots.url;"
        }

        /**
         * Devuelve la query que busca los juegos con un límite de 50, ordenados por valoración.
         */
        fun getGamesQuery(currentPage: Int): String {
            val offset = calculateOffset(currentPage)
            return  "id, name, first_release_date,summary, storyline, cover.url, platforms.name," +
                    "platforms.platform_logo.url, category, genres.name,rating, rating_count, total_rating," +
                    "total_rating_count,url,screenshots.url;limit $GAME_FETCH_NUMBER; offset $offset; sort rating desc;  where category = (0, 1, 8, 9) & rating != null & total_rating_count >= 100"
        }

    }
}