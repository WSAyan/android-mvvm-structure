package com.wsayan.mvvmstructure.repo

import com.wsayan.mvvmstructure.di.DataManager
import com.wsayan.mvvmstructure.network.convertData
import com.wsayan.mvvmstructure.network.data.MovieListResponse
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    val dataManager: DataManager
) : IMoviesRepository {

    override suspend fun fetchPopularMovies(): MovieListResponse {
        val hashMap = HashMap<String, String>()
        hashMap["api_key"] = "ff828a72b45f8a8bc8835e4999ee3f6a"
        return dataManager
            .apiService
            .getRequest("3/movie/popular", hashMap)
            .convertData(MovieListResponse::class) as MovieListResponse
    }
}