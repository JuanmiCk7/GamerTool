package com.juanmi.gamertool.pagging.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.juanmi.gamertool.application.api.ResultData
import com.juanmi.gamertool.model.Game
import com.juanmi.gamertool.repository.retrofit.GameRepository
import retrofit2.HttpException

private const val STARTING_PAGE_INDEX = 1

/***
 * Clase utilizada para cargar una página en base a una búsqueda por nombre de un juego.
 */
class GameSearchPagingSource(private val repository: GameRepository, private val gameName: String) :
    PagingSource<Int, Game>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        return try {

            val pageNumber = params.key ?: STARTING_PAGE_INDEX
            val response = repository.getGamesByName(gameName, pageNumber)

            var resultData: ArrayList<Game>? = null
            if (response is ResultData.Success) {
                resultData = response.data
            }

            LoadResult.Page(
                data = resultData!!.toList(),
                prevKey = if(pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = if (resultData.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}