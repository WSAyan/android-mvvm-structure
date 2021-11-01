package com.wsayan.mvvmstructure.network.data

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConfigurationResponse(
    @SerializedName("images") val images: Images? = null,
    @SerializedName("change_keys") val change_keys: List<String>? = null
) : BaseResponse()