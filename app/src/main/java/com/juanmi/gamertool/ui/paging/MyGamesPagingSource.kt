package com.juanmi.gamertool.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.FirebaseFirestore
import com.juanmi.gamertool.core.network.ResultData
import com.juanmi.gamertool.repository.model.Game

class MyGamesPagingSource
    : PagingSource<Int, Game>() {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, Game> {
        val pageNumber = params.key ?: 1
        var resultData = ArrayList<Game>()

        return try {
            db.collection("games").get().addOnSuccessListener {
                games -> for (game in games) {
                    resultData!!.add(game.toObject(Game::class.java))
            }
            }

            var nextPageNumber: Int? = null
            nextPageNumber = pageNumber + 1

            PagingSource.LoadResult.Page(
                data = resultData!!.toList(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            PagingSource.LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}