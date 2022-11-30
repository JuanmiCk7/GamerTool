package com.juanmi.gamertool.repository.retrofit

import com.juanmi.gamertool.model.Game
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query


/**
 * Interfaz que define los métodos con los que haremos las peticiones gracias
 * a las anotaciones de Retrofit2.
 */
interface GameService {
    @POST("games")
    suspend fun getGames(
        @Query("fields") fields: String
    ): Response<ArrayList<Game>>

    @POST("games")
    suspend fun getGamesByName(
        @Query("search") gameName: String,
        @Query("fields") fields: String
    ): Response<ArrayList<Game>>
}