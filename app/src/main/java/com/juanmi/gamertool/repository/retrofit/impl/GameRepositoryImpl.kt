package com.juanmi.gamertool.repository.retrofit.impl

import com.juanmi.gamertool.application.api.ResultData
import com.juanmi.gamertool.utils.safeCall
import com.juanmi.gamertool.model.Game
import com.juanmi.gamertool.repository.retrofit.GameRepository
import com.juanmi.gamertool.repository.retrofit.service.GameService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
        return safeCall(dispatcher) { service.getGames(getGamesQuery(currentPage)) }
    }

    /**
     * Método que devuelve un ArrayList de juegos obtenidos por nombre de la llamada a la API.
     *
     * params{name: Nombre del juego que se está buscando}
     */
    override suspend fun getGamesByName(name: String, currentPage: Int): ResultData<ArrayList<Game>?> {
        return safeCall(dispatcher) { service.getGamesByName(name , getGamesByNameQuery(currentPage)) }
    }

    companion object {


        const val GAME_PAGE_SIZE = 50
        private const val GAME_PAGE_SIZE_TO_QUERY_BY_NAME = 10

        /**
         * Calcula el offset para la query
         */
        private fun calculateOffset(currentPage: Int): Int {
            return (currentPage - 1) * GAME_PAGE_SIZE
        }

        /**
         * Devuelve la query con los campos que se van a buscar
         */
        fun getGamesByNameQuery(currentPage: Int) : String {
            val offset = calculateOffset(currentPage)
            return "id, name, first_release_date,summary, storyline, cover.url, involved_companies.company.name, platforms.name, follows," +
                    "category, genres.name,rating, rating_count, total_rating," +
                    "total_rating_count, url, screenshots.url; limit $GAME_PAGE_SIZE_TO_QUERY_BY_NAME; offset $offset; where category = (0, 1, 8, 9)"
        }

        /**
         * Devuelve la query que busca los juegos con un límite de 50, ordenados por valoración.
         */
        fun getGamesQuery(currentPage: Int): String {
            val offset = calculateOffset(currentPage)
            return  "id, name, first_release_date,summary, storyline, cover.url, involved_companies.company.name ,platforms.name, follows," +
                    "category, genres.name,rating, rating_count, total_rating," +
                    "total_rating_count,url,screenshots.url;limit $GAME_PAGE_SIZE; offset $offset; sort rating desc;" +
                    "  where category = (0, 1, 8, 9) & rating != null & total_rating_count >= 100"
        }

    }
}