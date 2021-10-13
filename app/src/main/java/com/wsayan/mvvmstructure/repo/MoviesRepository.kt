package com.wsayan.mvvmstructure.repo

import com.wsayan.mvvmstructure.di.DataManager
import com.wsayan.mvvmstructure.network.DataResult
import com.wsayan.mvvmstructure.network.data.MovieListResponse
import com.wsayan.mvvmstructure.util.convertData
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    val dataManager: DataManager
) : IMoviesRepository {

    override suspend fun fetchPopularMovies2(): DataResult<MovieListResponse> {
        val hashMap = HashMap<String, String>()
        hashMap["api_key"] = "ff828a72b45f8a8bc8835e4999ee3f6a"
        return try {
            val response = dataManager
                .apiService
                .getRequest("3/movie/popular", hashMap)

            if (response.code() == 401) {
                return DataResult.error(
                    message = "Unauthorized!",
                    shouldLogout = true
                )
            }

            if (response.isSuccessful) {
                val data = response.convertData(MovieListResponse::class) as MovieListResponse
                return DataResult.success(data = data)
            } else {
                DataResult.error(message = "Something went wrong!")
            }
        } catch (e: Exception) {
            DataResult.error(error = e, message = "Something went wrong!")
        }
    }

    override suspend fun fetchPopularMovies(): MovieListResponse {
        val hashMap = HashMap<String, String>()
        hashMap["api_key"] = "ff828a72b45f8a8bc8835e499ee3f6a"
        return dataManager
            .apiService
            .getRequest("3/movie/popular", hashMap)
            .convertData(MovieListResponse::class) as MovieListResponse
    }
}