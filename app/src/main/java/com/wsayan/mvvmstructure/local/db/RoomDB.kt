package com.wsayan.mvvmstructure.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wsayan.mvvmstructure.local.db.dao.CategoryDao
import com.wsayan.mvvmstructure.local.db.entity.Category


@Database(entities = arrayOf(Category::class) , version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
}