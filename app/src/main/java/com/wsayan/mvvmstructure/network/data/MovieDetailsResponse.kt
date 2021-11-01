package com.wsayan.mvvmstructure.network.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetailsResponse(
    @SerializedName("adult") val adult: Boolean? = null,
    @SerializedName("backdrop_path") val backdrop_path: String? = null,
    @SerializedName("budget") val budget: Int? = null,
    @SerializedName("genres") val genres: List<Genres>? = null,
    @SerializedName("homepage") val homepage: String? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("imdb_id") val imdb_id: String? = null,
    @SerializedName("original_language") val original_language: String? = null,
    @SerializedName("original_title") val original_title: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("popularity") val popularity: Double? = null,
    @SerializedName("poster_path") val poster_path: String? = null,
    @SerializedName("production_companies") val production_companies: List<ProductionCompanies>? = null,
    @SerializedName("production_countries") val production_countries: List<ProductionCountries>? = null,
    @SerializedName("release_date") val release_date: String? = null,
    @SerializedName("revenue") val revenue: Int? = null,
    @SerializedName("runtime") val runtime: Int? = null,
    @SerializedName("spoken_languages") val spoken_languages: List<SpokenLanguages>? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("tagline") val tagline: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("video") val video: Boolean? = null,
    @SerializedName("vote_average") val vote_average: Double? = null,
    @SerializedName("vote_count") val vote_count: Int? = null
) : BaseResponse(), Parcelable

@Parcelize
data class Genres(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null
) : Parcelable

@Parcelize
data class ProductionCompanies(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("logo_path") val logo_path: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("origin_country") val origin_country: String? = null
) : Parcelable

@Parcelize
data class ProductionCountries(
    @SerializedName("iso_3166_1") val iso_3166_1: String? = null,
    @SerializedName("name") val name: String? = null
) : Parcelable

@Parcelize
data class SpokenLanguages(
    @SerializedName("iso_639_1") val iso_639_1: String? = null,
    @SerializedName("name") val name: String? = null
) : Parcelable