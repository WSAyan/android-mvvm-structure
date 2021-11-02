package com.wsayan.mvvmstructure.repo

import com.wsayan.mvvmstructure.db.entity.ImagesConfig
import com.wsayan.mvvmstructure.network.data.ConfigurationResponse
import com.wsayan.mvvmstructure.network.data.Images
import com.wsayan.mvvmstructure.network.data.MovieDetailsResponse
import com.wsayan.mvvmstructure.network.data.MovieListResponse
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {
    suspend fun fetchPopularMovies(): MovieListResponse
    suspend fun fetchPopularMovies(page: Int): MovieListResponse
    suspend fun fetchMovieDetails(id: Int): MovieDetailsResponse
    suspend fun fetchConfigurations(): ConfigurationResponse
    suspend fun insertImageConfig(images: Images)
    fun selectImageConfig(): Flow<ImagesConfig>
    fun isEmptyImageConfig(): Flow<Boolean>
    fun cacheImageBaseUrl(url: String)
    val imageBaseUrl: String
}