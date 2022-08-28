package com.wsayan.mvvmstructure.repo

import com.wsayan.mvvmstructure.db.RoomHelper
import com.wsayan.mvvmstructure.network.AppNetworkState
import com.wsayan.mvvmstructure.network.IApiService
import com.wsayan.mvvmstructure.network.resolveError
import com.wsayan.mvvmstructure.preference.PreferencesHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseRepository : IBaseRepository {
    abstract var apiService: IApiService
    abstract var preferencesHelper: PreferencesHelper
    abstract var roomHelper: RoomHelper

    protected suspend fun <T : Any> handleNetworkCall(callback: suspend () -> T): Flow<AppNetworkState<T>> =
        flow {
            emit(AppNetworkState.Loading)

            try {
                val networkCall = AppNetworkState.Data(callback())

                emit(networkCall)
            } catch (e: Exception) {
                e.printStackTrace()

                emit(e.resolveError())
            }
        }

    override fun cacheImageBaseUrl(url: String) =
        preferencesHelper.put("image_base", url)

    override fun imageBaseUrl() = preferencesHelper["image_base", ""]
}