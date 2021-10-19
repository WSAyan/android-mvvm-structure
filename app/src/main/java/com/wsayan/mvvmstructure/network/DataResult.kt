package com.wsayan.mvvmstructure.network

import com.wsayan.mvvmstructure.network.data.BaseResponse
import com.wsayan.mvvmstructure.util.convertBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.reflect.KClass

class DataResult<out T>(
    val status: Status,
    val data: T? = null,
    val error: Throwable? = null,
    val message: String? = null,
    val shouldLogout: Boolean = false
) {
    companion object {
        fun <T> success(data: T? = null) = DataResult(
            status = Status.SUCCESS,
            data = data
        )

        fun loading(
            message: String? = null
        ) = DataResult(
            status = Status.LOADING,
            data = null,
            message = message
        )

        fun <T> error(
            error: Exception? = null,
            message: String? = null,
            data: T? = null,
            shouldLogout: Boolean = false
        ) = DataResult(
            status = Status.ERROR,
            data = data,
            error = error,
            message = message,
            shouldLogout = shouldLogout
        )
    }

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

}

sealed class NetworkState<out T> {
    object Loading : NetworkState<Nothing>()
    data class Error(var exception: Throwable) : NetworkState<Nothing>()
    data class Data<T>(var data: T) : NetworkState<T>()
}

open class NetworkErrorExceptions(
    val errorCode: Int = -1,
    val errorMessageRes: Int? = null,
    val errorMessage: String? = null,
    val errorBody: BaseResponse? = null,
    val unauthorized: Boolean = false
) : Exception() {
    override val message: String?
        get() = errorMessage

    companion object {
        fun parseException(exception: HttpException): NetworkErrorExceptions {
            return try {
                val errorBody =
                    exception.response()?.errorBody()
                        ?.convertBody(BaseResponse::class) as BaseResponse

                NetworkErrorExceptions(
                    errorCode = exception.code(),
                    errorMessage = errorBody.status_message,
                    errorBody = errorBody,
                    unauthorized = exception.code() == 401 // unauthorized true if 401
                )
            } catch (_: Exception) {
                NetworkErrorExceptions(
                    errorCode = exception.code(),
                    errorMessage = "unexpected error!!"
                )
            }
        }
    }
}

fun Exception.resolveError(): NetworkState.Error {
    when (this) {
        is SocketTimeoutException -> {
            return NetworkState.Error(NetworkErrorExceptions(errorMessage = "connection error!"))
        }
        is ConnectException -> {
            return NetworkState.Error(NetworkErrorExceptions(errorMessage = "no internet access!"))
        }
        is UnknownHostException -> {
            return NetworkState.Error(NetworkErrorExceptions(errorMessage = "no internet access!"))
        }
        is HttpException -> {
            return NetworkState.Error(NetworkErrorExceptions.parseException(this))
        }
    }

    return NetworkState.Error(this)
}

fun <T : Any> ResponseBody.resolveData(classType: KClass<T>): NetworkState<T> {
    return try {
        NetworkState.Data(this.convertBody(classType) as T)
    } catch (exception: Exception) {
        exception.resolveError()
    }
}

