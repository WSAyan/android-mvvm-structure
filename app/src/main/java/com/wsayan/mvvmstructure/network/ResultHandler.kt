package com.wsayan.mvvmstructure.network

import com.google.gson.GsonBuilder
import com.wsayan.mvvmstructure.network.data.BaseResponse
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.reflect.KClass

sealed class NetworkState<out T> {
    object Loading : NetworkState<Nothing>()

    data class Error(
        var exception: Throwable,
        var errorBody: BaseResponse? = null,
        var unauthorized: Boolean = false
    ) : NetworkState<Nothing>()

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
            val exception = NetworkErrorExceptions(errorMessage = "connection error!")
            return NetworkState.Error(
                exception = exception,
                errorBody = exception.errorBody,
                unauthorized = exception.unauthorized
            )
        }
        is ConnectException -> {
            val exception = NetworkErrorExceptions(errorMessage = "no internet access!")
            return NetworkState.Error(
                exception = exception,
                errorBody = exception.errorBody,
                unauthorized = exception.unauthorized
            )
        }
        is UnknownHostException -> {
            val exception = NetworkErrorExceptions(errorMessage = "no internet access!")
            return NetworkState.Error(
                exception = exception,
                errorBody = exception.errorBody,
                unauthorized = exception.unauthorized
            )
        }
        is HttpException -> {
            val exception = NetworkErrorExceptions.parseException(this)
            return NetworkState.Error(
                exception = exception,
                errorBody = exception.errorBody,
                unauthorized = exception.unauthorized
            )
        }
    }

    return NetworkState.Error(
        exception = this,
        errorBody = null,
        unauthorized = false
    )
}

fun <T : Any> Response<ResponseBody>.convertData(classType: KClass<T>): Any {
    val body = if (this.isSuccessful) {
        this.body()?.string()
    } else throw HttpException(this)

    return GsonBuilder().serializeNulls().create().fromJson(
        body,
        classType.java
    )
}

fun <T : Any> ResponseBody.convertBody(classType: KClass<T>): Any {
    return GsonBuilder().serializeNulls().create().fromJson(
        this.string(),
        classType.java
    )
}

