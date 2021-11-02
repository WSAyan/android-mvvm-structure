package com.wsayan.mvvmstructure.repo

import com.wsayan.mvvmstructure.BuildConfig
import com.wsayan.mvvmstructure.db.entity.ImagesConfig
import com.wsayan.mvvmstructure.di.DataManager
import com.wsayan.mvvmstructure.network.convertData
import com.wsayan.mvvmstructure.network.data.ConfigurationResponse
import com.wsayan.mvvmstructure.network.data.Images
import com.wsayan.mvvmstructure.network.data.MovieDetailsResponse
import com.wsayan.mvvmstructure.network.data.MovieListResponse
import kotlinx.coroutines.flow.Flow
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

    override suspend fun fetchConfigurations(): ConfigurationResponse {
        val hashMap = HashMap<String, String>()
        hashMap["api_key"] = BuildConfig.API_KEY
        return dataManager
            .apiService
            .getRequest("3/configuration", hashMap)
            .convertData(ConfigurationResponse::class) as ConfigurationResponse
    }

    override suspend fun insertImageConfig(images: Images) {
        dataManager
            .roomHelper
            .getDatabase()
            .imageConfigDao()
            .insert(
                ImagesConfig(
                    base_url = images.base_url,
                    secure_base_url = images.secure_base_url,
                    backdrop_sizes = images.backdrop_sizes?.joinToString(separator = ","),
                    logo_sizes = images.logo_sizes?.joinToString(separator = ","),
                    poster_sizes = images.poster_sizes?.joinToString(separator = ","),
                    profile_sizes = images.profile_sizes?.joinToString(separator = ","),
                    still_sizes = images.still_sizes?.joinToString(separator = ",")
                )
            )
    }

    override fun selectImageConfig(): Flow<ImagesConfig> =
        dataManager
            .roomHelper
            .getDatabase()
            .imageConfigDao()
            .getLatestConfig()

    override fun isEmptyImageConfig(): Flow<Boolean> =
        dataManager
            .roomHelper
            .getDatabase()
            .imageConfigDao()
            .isEmptyConfig()

    override fun cacheImageBaseUrl(url: String) =
        dataManager.preferencesHelper.put("image_base", url)

    override val imageBaseUrl = dataManager.preferencesHelper["image_base", ""]

}