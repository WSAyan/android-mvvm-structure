package com.wsayan.mvvmstructure.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImagesConfig(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "base_url")
    var base_url: String? = null,

    @ColumnInfo(name = "secure_base_url")
    var secure_base_url: String? = null,

    @ColumnInfo(name = "backdrop_sizes")
    var backdrop_sizes: String? = null,

    @ColumnInfo(name = "logo_sizes")
    var logo_sizes: String? = null,

    @ColumnInfo(name = "poster_sizes")
    var poster_sizes: String? = null,

    @ColumnInfo(name = "profile_sizes")
    var profile_sizes: String? = null,

    @ColumnInfo(name = "still_sizes")
    var still_sizes: String? = null
)
