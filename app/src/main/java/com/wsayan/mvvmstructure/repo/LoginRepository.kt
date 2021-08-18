package com.wsayan.mvvmstructure.repo

import com.wsayan.mvvmstructure.DataManager
import com.wsayan.mvvmstructure.network.ApiHelper
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(val dataManager: DataManager) {
    suspend fun apiLogin(
        username: String,
        password: String
    ): Response<ResponseBody> {
        val hashMap = HashMap<String, String>()
        return dataManager.apiHelper.apiCalls(
            ApiHelper.CALL_TYPE_POST,
            ApiHelper.ENDPOINT_LOGIN,
            hashMap
        )
    }
}