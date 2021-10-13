package com.wsayan.mvvmstructure.network

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
