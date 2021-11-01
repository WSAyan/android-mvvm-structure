package com.wsayan.mvvmstructure.network.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BaseResponse(
    @SerializedName("success") val success: Boolean? = null,
    @SerializedName("status_code") val status_code: Int? = null,
    @SerializedName("status_message") val status_message: String? = null
) : Parcelable
