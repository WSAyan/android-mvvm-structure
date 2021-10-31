package com.wsayan.mvvmstructure.repo

import com.wsayan.mvvmstructure.network.data.MovieListResponse

interface IMoviesRepository {
    suspend fun fetchPopularMovies(): MovieListResponse
}