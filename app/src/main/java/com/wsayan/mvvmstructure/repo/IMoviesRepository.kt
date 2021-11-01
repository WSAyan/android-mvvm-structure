package com.wsayan.mvvmstructure.repo

import com.wsayan.mvvmstructure.network.data.MovieDetailsResponse
import com.wsayan.mvvmstructure.network.data.MovieListResponse

interface IMoviesRepository {
    suspend fun fetchPopularMovies(): MovieListResponse
    suspend fun fetchPopularMovies(page: Int): MovieListResponse
    suspend fun fetchMovieDetails(id: Int): MovieDetailsResponse
}