package com.wsayan.mvvmstructure.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wsayan.mvvmstructure.network.data.ResultsItem
import com.wsayan.mvvmstructure.repo.MoviesRepository

class PostDataSource(private val moviesRepository: MoviesRepository) :
    PagingSource<Int, ResultsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        try {
            val currentPage = params.key ?: 1

            val response = moviesRepository.fetchPopularMovies(currentPage)

            return LoadResult.Page(
                data = response.results,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (currentPage < response.totalPages ?: 1)
                    response.page?.plus(1) else null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}