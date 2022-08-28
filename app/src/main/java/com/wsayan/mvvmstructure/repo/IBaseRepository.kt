package com.wsayan.mvvmstructure.repo

interface IBaseRepository {
    fun cacheImageBaseUrl(url: String)

    fun imageBaseUrl(): String
}