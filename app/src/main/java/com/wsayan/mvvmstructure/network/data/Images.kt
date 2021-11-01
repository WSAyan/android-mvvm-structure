package com.wsayan.mvvmstructure.network.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Images(
    @SerializedName("base_url") val base_url: String? = null,
    @SerializedName("secure_base_url") val secure_base_url: String? = null,
    @SerializedName("backdrop_sizes") val backdrop_sizes: List<String>? = null,
    @SerializedName("logo_sizes") val logo_sizes: List<String>? = null,
    @SerializedName("poster_sizes") val poster_sizes: List<String>? = null,
    @SerializedName("profile_sizes") val profile_sizes: List<String>? = null,
    @SerializedName("still_sizes") val still_sizes: List<String>? = null
) : Parcelable
