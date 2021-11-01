package com.wsayan.mvvmstructure.repo

import com.wsayan.mvvmstructure.BuildConfig
import com.wsayan.mvvmstructure.di.DataManager
import com.wsayan.mvvmstructure.network.convertData
import com.wsayan.mvvmstructure.network.data.MovieDetailsResponse
import com.wsayan.mvvmstructure.network.data.MovieListResponse
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    val dataManager: DataManager
) : IMoviesRepository {

    override suspend fun fetchPopularMovies(): MovieListResponse {
        val hashMap = HashMap<String, String>()
        hashMap["api_key"] = BuildConfig.API_KEY
        return dataManager
            .apiService
            .getRequest("3/movie/popular", hashMap)
            .convertData(MovieListResponse::class) as MovieListResponse
    }

    override suspend fun fetchPopularMovies(page: Int): MovieListResponse {
        val hashMap = HashMap<String, String>()
        hashMap["api_key"] = BuildConfig.API_KEY
        hashMap["page"] = page.toString()
        return dataManager
            .apiService
            .getRequest("3/movie/popular", hashMap)
            .convertData(MovieListResponse::class) as MovieListResponse
    }

    override suspend fun fetchMovieDetails(id: Int): MovieDetailsResponse {
        val hashMap = HashMap<String, String>()
        hashMap["api_key"] = BuildConfig.API_KEY
        return dataManager
            .apiService
            .getRequest("3/movie/$id", hashMap)
            .convertData(MovieDetailsResponse::class) as MovieDetailsResponse
    }

}