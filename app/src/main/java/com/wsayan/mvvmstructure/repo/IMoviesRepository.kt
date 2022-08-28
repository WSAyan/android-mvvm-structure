package com.wsayan.mvvmstructure.repo

import com.wsayan.mvvmstructure.db.entity.ImagesConfig
import com.wsayan.mvvmstructure.network.AppNetworkState
import com.wsayan.mvvmstructure.network.data.ConfigurationResponse
import com.wsayan.mvvmstructure.network.data.Images
import com.wsayan.mvvmstructure.network.data.MovieDetailsResponse
import com.wsayan.mvvmstructure.network.data.MovieListResponse
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository : IBaseRepository {
    suspend fun fetchPopularMovies(): Flow<AppNetworkState<MovieListResponse>>
    suspend fun fetchPopularMovies(page: Int): MovieListResponse
    suspend fun fetchMovieDetails(id: Int): Flow<AppNetworkState<MovieDetailsResponse>>
    suspend fun fetchConfigurations(): Flow<AppNetworkState<ConfigurationResponse>>
    suspend fun insertImageConfig(images: Images)
    fun selectImageConfig(): Flow<ImagesConfig>
    fun isEmptyImageConfig(): Flow<Boolean>
}