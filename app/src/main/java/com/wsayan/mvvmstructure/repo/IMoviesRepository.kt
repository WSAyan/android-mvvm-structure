package com.wsayan.mvvmstructure.repo

import com.wsayan.mvvmstructure.network.DataResult
import com.wsayan.mvvmstructure.network.data.MovieListResponse
import com.wsayan.mvvmstructure.network.data.ResultsItem
import okhttp3.ResponseBody
import retrofit2.Response

interface IMoviesRepository {
    suspend fun fetchPopularMovies(): MovieListResponse
    suspend fun fetchPopularMovies2(): DataResult<MovieListResponse>
    suspend fun fetchPopularMovies3(): MovieListResponse
}