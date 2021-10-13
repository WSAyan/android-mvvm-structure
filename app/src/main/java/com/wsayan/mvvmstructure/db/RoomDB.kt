package com.wsayan.mvvmstructure.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wsayan.mvvmstructure.db.dao.CategoryDao
import com.wsayan.mvvmstructure.db.entity.Category


@Database(entities = arrayOf(Category::class) , version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
}