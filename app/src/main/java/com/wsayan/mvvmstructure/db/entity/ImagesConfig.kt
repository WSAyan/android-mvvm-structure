package com.wsayan.mvvmstructure.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class ImagesConfig(
    @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "base_url") var base_url: String? = null,
    @ColumnInfo(name = "secure_base_url") var secure_base_url: String? = null,
    @ColumnInfo(name = "backdrop_sizes") var backdrop_sizes: List<String>? = null,
    @ColumnInfo(name = "logo_sizes") var logo_sizes: List<String>? = null,
    @ColumnInfo(name = "poster_sizes") var poster_sizes: List<String>? = null,
    @ColumnInfo(name = "profile_sizes") var profile_sizes: List<String>? = null,
    @ColumnInfo(name = "still_sizes") var still_sizes: List<String>? = null
)
