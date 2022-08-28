package com.wsayan.mvvmstructure.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.wsayan.mvvmstructure.network.AppNetworkState
import com.wsayan.mvvmstructure.util.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _showProgressBar = MutableStateFlow(false)
    val showProgressBar = _showProgressBar.asStateFlow()

    private val _unauthorized = MutableStateFlow(false)
    val unauthorized = _unauthorized.asStateFlow()

    private val _uiState: MutableStateFlow<UIState<Any>?> = MutableStateFlow(null)
    val uiState: StateFlow<UIState<Any>?> = _uiState

    protected fun <T : Any> generateUiState(stateId: Int, state: AppNetworkState<T>) {
        when (state) {
            is AppNetworkState.Loading -> {
                _uiState.value = UIState.Loading(stateId = stateId)

                _showProgressBar.value = true

                _unauthorized.value = false
            }
            is AppNetworkState.Data -> {
                _uiState.value = UIState.DataLoaded(stateId = stateId, data = state.data)

                _showProgressBar.value = false

                _unauthorized.value = false
            }
            is AppNetworkState.Error -> {
                _uiState.value =
                    UIState.Error(
                        stateId = stateId,
                        message = state.exception.errorMessage ?: "",
                        unAuthorized = state.unauthorized
                    )

                _showProgressBar.value = false

                _unauthorized.value = state.unauthorized
            }
        }
    }
}

sealed class UIState<out T> {
    data class DataLoaded<out T>(val stateId: Int, val data: T) : UIState<T>()

    data class Loading(val stateId: Int) : UIState<Nothing>()

    data class Error(val stateId: Int, var message: String, var unAuthorized: Boolean = false) :
        UIState<Nothing>()

}