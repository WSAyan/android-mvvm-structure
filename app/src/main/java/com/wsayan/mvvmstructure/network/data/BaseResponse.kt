package com.wsayan.mvvmstructure.network.data

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("success") val success: Boolean? = null,
    @SerializedName("status_code") val status_code: Int? = null,
    @SerializedName("status_message") val status_message: String? = null
)
