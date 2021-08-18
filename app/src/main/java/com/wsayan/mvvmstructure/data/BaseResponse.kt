package com.wsayan.mvvmstructure.data

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @field:SerializedName("status")
    val status: Int? = null,
    @field:SerializedName("message")
    val message: Int? = null,
    @field:SerializedName("errors")
    val errors: List<String>? = null,
)
