package com.juanmi.gamertool.ui.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.juanmi.gamertool.repository.model.Game
import kotlinx.coroutines.tasks.await


class MyGamesPagingSource
    : PagingSource<Int, Game>() {

    var games: ArrayList<Game>? = null
    private val db = FirebaseFirestore.getInstance()

    override suspend fun load(params: PagingSource.LoadParams<Int>): LoadResult<Int, Game> {

        return try {
            LoadResult.Page(
                data = db.collection("games").get().await().toObjects(Game::class.java),
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}