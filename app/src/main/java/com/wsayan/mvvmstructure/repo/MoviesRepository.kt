package com.wsayan.mvvmstructure.repo

import com.wsayan.mvvmstructure.BuildConfig
import com.wsayan.mvvmstructure.db.RoomHelper
import com.wsayan.mvvmstructure.db.entity.ImagesConfig
import com.wsayan.mvvmstructure.network.AppNetworkState
import com.wsayan.mvvmstructure.network.IApiService
import com.wsayan.mvvmstructure.network.convertData
import com.wsayan.mvvmstructure.network.data.ConfigurationResponse
import com.wsayan.mvvmstructure.network.data.Images
import com.wsayan.mvvmstructure.network.data.MovieDetailsResponse
import com.wsayan.mvvmstructure.network.data.MovieListResponse
import com.wsayan.mvvmstructure.preference.PreferencesHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    override var apiService: IApiService,
    override var preferencesHelper: PreferencesHelper,
    override var roomHelper: RoomHelper
) : IMoviesRepository, BaseRepository() {

    override suspend fun fetchPopularMovies(): Flow<AppNetworkState<MovieListResponse>> {
        val hashMap = HashMap<String, String>()
        hashMap["api_key"] = BuildConfig.API_KEY
        return handleNetworkCall {
            apiService
                .getRequest("3/movie/popular", hashMap)
                .convertData(MovieListResponse::class) as MovieListResponse
        }
    }

    override suspend fun fetchPopularMovies(page: Int): MovieListResponse {
        val hashMap = HashMap<String, String>()
        hashMap["api_key"] = BuildConfig.API_KEY
        hashMap["page"] = page.toString()
        return apiService
            .getRequest("3/movie/popular", hashMap)
            .convertData(MovieListResponse::class) as MovieListResponse
    }

    override suspend fun fetchMovieDetails(id: Int): Flow<AppNetworkState<MovieDetailsResponse>> {
        val hashMap = HashMap<String, String>()
        hashMap["api_key"] = BuildConfig.API_KEY
        return handleNetworkCall {
            apiService
                .getRequest("3/movie/$id", hashMap)
                .convertData(MovieDetailsResponse::class) as MovieDetailsResponse
        }
    }

    override suspend fun fetchConfigurations(): Flow<AppNetworkState<ConfigurationResponse>> {
        val hashMap = HashMap<String, String>()
        hashMap["api_key"] = BuildConfig.API_KEY
        return handleNetworkCall {
            apiService
                .getRequest("3/configuration", hashMap)
                .convertData(ConfigurationResponse::class) as ConfigurationResponse
        }
    }

    override suspend fun insertImageConfig(images: Images) {
        roomHelper
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
        roomHelper
            .getDatabase()
            .imageConfigDao()
            .getLatestConfig()

    override fun isEmptyImageConfig(): Flow<Boolean> =
        roomHelper
            .getDatabase()
            .imageConfigDao()
            .isEmptyConfig()

}