package com.wsayan.mvvmstructure.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.wsayan.mvvmstructure.util.SingleLiveEvent
import okhttp3.ResponseBody
import retrofit2.Response
import kotlin.reflect.KClass

abstract class BaseViewModel : ViewModel() {

    private val _isLoading = SingleLiveEvent<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    var forceLogOut = SingleLiveEvent<Boolean>()

    fun onLoading(isLoader: Boolean) {
        _isLoading.value = isLoader
    }

    fun forceLogOut(flag: Boolean) {
        forceLogOut.value = flag
    }

    fun <T : Any> convertData(classType: KClass<T>, response: Response<ResponseBody>): Any {
        val body = if (response.isSuccessful) {
            response.body()?.string()
        } else {
            response.errorBody()?.string()
        }

        return Gson().fromJson(
            body,
            classType.java
        )
    }
}