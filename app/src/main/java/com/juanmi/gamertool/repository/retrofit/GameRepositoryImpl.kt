package com.juanmi.gamertool.repository.retrofit

import com.juanmi.gamertool.application.api_utils.ResultData
import com.juanmi.gamertool.application.safeCall
import com.juanmi.gamertool.repository.model.Game
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Clase que implementa los métodos utilizados para hacer las peticiones a la API IGDB.
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
        return withContext(dispatcher) {
            return@withContext safeCall {
                service.getGames(getGamesQuery(currentPage))
            }
        }
    }

    /**
     * Método que devuelve un ArrayList de juegos obtenidos por nombre de la llamada a la API.
     *
     * params{name: Nombre del juego que se está buscando}
     */
    override suspend fun getGamesByName(name: String): ResultData<ArrayList<Game>?> {
        return withContext(dispatcher) {
            return@withContext safeCall {
                service.getGamesByName(name ,getGamesByNameQuery())
            }
        }
    }

    companion object {


        const val GAME_FETCH_NUMBER = 50
        private const val GAME_FETCH_NUMBER_TO_QUERY_BY_NAME = 30

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
                    "total_rating_count,url,screenshots.url; limit $GAME_FETCH_NUMBER_TO_QUERY_BY_NAME; where category = (0, 1, 8, 9)"
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